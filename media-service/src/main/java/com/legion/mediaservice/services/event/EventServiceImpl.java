package com.legion.mediaservice.services.event;

import com.legion.mediaservice.broker.broadcast.dtos.sent.EventCreatedBrokerDTO;
import com.legion.mediaservice.broker.broadcast.dtos.sent.EventParticipationDTO;
import com.legion.mediaservice.controller.NotFoundException;
import com.legion.mediaservice.dto.ImageDTO;
import com.legion.mediaservice.dto.event.CreateEventDTO;
import com.legion.mediaservice.dto.event.EventDTO;
import com.legion.mediaservice.dto.feed.FeedDTO;
import com.legion.mediaservice.dto.feed.GenericFeedSetDTO;
import com.legion.mediaservice.dto.feed.partials.StoryDTO;
import com.legion.mediaservice.entities.User;
import com.legion.mediaservice.entities.event.Event;
import com.legion.mediaservice.entities.image.EventImage;
import com.legion.mediaservice.mappers.EventImageMapper;
import com.legion.mediaservice.mappers.EventMapper;
import com.legion.mediaservice.mappers.UserMapper;
import com.legion.mediaservice.repositories.EventImageRepository;
import com.legion.mediaservice.repositories.EventRepository;
import com.legion.mediaservice.services.user.UserService;
import com.legion.mediaservice.utils.PageRequestBuilder;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventImageRepository eventImageRepository;
    private final EventMapper eventMapper;
    private final UserMapper userMapper;
    private final EventImageMapper imageMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    private final UserService userService;

    private final ObservationRegistry observationRegistry;

    @Override
    public Page<EventDTO> listEvents(String creatorUid, String eventContent, Integer page, Integer pageSize) {

        PageRequest pageRequest = new PageRequestBuilder().buildPageRequest(page, pageSize);
        Page<Event> postPage;

        if (StringUtils.hasText(creatorUid.toString())) {
            postPage = listEventsByUser(creatorUid, pageRequest);
        } else if (!StringUtils.hasText(creatorUid.toString()) && (StringUtils.hasText(eventContent))) {
            postPage = listEventsByUser(creatorUid, pageRequest);
        } else if (StringUtils.hasText(creatorUid.toString()) && (StringUtils.hasText(eventContent))) {
            postPage = listEventsByUser(creatorUid, pageRequest);
        } else {
            postPage = eventRepository.findAll(pageRequest);
        }
        return postPage.map(eventMapper::eventToEventDTO);
    }

    private Page<Event> listEventsByUser(String creatorUid, Pageable pageable) {
        return eventRepository.findByCreatorUid(creatorUid, pageable);
    }

    private Page<Event> listEventsByUserAndContent(UUID creatorId, String content, Pageable pageable) {
        return eventRepository.findByCreator_IdEqualsAndDetailsContains(creatorId, content, pageable);
    }

    private Page<Event> listEventsByContent(String content, Pageable pageable) {
        return eventRepository.findByDetailsContains(content, pageable);
    }


    @Override
    public FeedDTO getFeed(Integer page, Integer pageSize) {
        PageRequest pageRequest = new PageRequestBuilder().buildPageRequest(page, pageSize);
        List<Event> eventPage;

        eventPage = eventRepository.findAll();

        FeedDTO responseFeedDTO = new FeedDTO();

        GenericFeedSetDTO feedEventsDTO = new GenericFeedSetDTO("events");

        feedEventsDTO.getFeedDTOSet().addAll(eventPage.stream().map(eventMapper::eventToEventDTO).collect(Collectors.toSet()));


        GenericFeedSetDTO feedRecommendedDTO = new GenericFeedSetDTO("recommended");
        GenericFeedSetDTO feedStoryDTO = new GenericFeedSetDTO("story");

        eventPage.forEach(item -> {

            StoryDTO storyDTO = new StoryDTO(item.getId(), item.getVersion(), userMapper.userToUserDTO(item.getCreator()));
            feedStoryDTO.getFeedDTOSet().add(storyDTO);

//            RecommendationDTO recommendationDTO = new RecommendationDTO(item.getId(), item.getVersion(), item.getName(), item.getName());
            EventDTO recommendationDTO = eventMapper.eventToEventDTO(item);
            feedRecommendedDTO.getFeedDTOSet().add(recommendationDTO);
        });

        responseFeedDTO.getFeed().add(feedStoryDTO);
        responseFeedDTO.getFeed().add(feedRecommendedDTO);
        responseFeedDTO.getFeed().add(feedEventsDTO);

        return responseFeedDTO;
    }

    @Override
    public Optional<EventDTO> getEventById(UUID id) {
        return Optional.ofNullable(eventMapper.eventToEventDTO(this.eventRepository.findById(id).orElse(null)));
    }

    @Override
    public EventDTO createEvent(CreateEventDTO eventDTO, String uid) {

        User user = userService.getUserByUid(uid).orElseThrow(NotFoundException::new);

        Event event = eventMapper.createdEventDtoToEvent(eventDTO);
        event.setCreator(user);

        Event savedEvent = eventRepository.save(event);

        Set<ImageDTO> imageDTOs = eventDTO.getImages();
        if (imageDTOs != null && !imageDTOs.isEmpty()) {
            for (ImageDTO imageDTO : imageDTOs) {
                EventImage eventImage = imageMapper.imageDtoToImage(imageDTO);
                event.addImage(eventImage);
                eventImageRepository.save(eventImage);
            }
        }

        publishEventCreationEvent(eventMapper.eventToCreatedEventDto(event));
        return eventMapper.eventToEventDTO(savedEvent);
    }

    @Override
    public Optional<EventDTO> participateInEvent(UUID eventId, String uid) {
        User user = userService.getUserByUid(uid).orElseThrow(NotFoundException::new);

        Observation serviceObservation = Observation.createNotStarted("media-service-lookup", this.observationRegistry);
        serviceObservation.lowCardinalityKeyValue("call", "media-service");

        return serviceObservation.observe(() -> {

            AtomicReference<Optional<EventDTO>> atomicReference = new AtomicReference<>();

            eventRepository.findById(eventId).ifPresentOrElse(event -> {
                Set<User> participantsSet = event.getParticipants();
                participantsSet.add(user);
                event.setParticipants(participantsSet);
                atomicReference.set(Optional.of(eventMapper.eventToEventDTO(eventRepository.save(event))));

                publishEventParticipationEvent(new EventParticipationDTO(event.getId(), user.getId()));
            }, () -> atomicReference.set(Optional.empty()));

            return atomicReference.get();
        });
    }

    @Override
    public Optional<EventDTO> updateEventById(UUID eventId, EventDTO dto) {
        AtomicReference<Optional<EventDTO>> atomicReference = new AtomicReference<>();

        eventRepository.findById(eventId).ifPresentOrElse(foundPost -> {
            foundPost.setDetails(dto.getDetails());
            atomicReference.set(Optional.of(eventMapper.eventToEventDTO(eventRepository.save(foundPost))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deleteById(UUID eventId) {
        if (eventRepository.existsById(eventId)) {
            eventRepository.deleteById(eventId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<EventDTO> patchEventById(UUID eventId, EventDTO eventDTO) {
        return Optional.empty();
    }


// ██████╗ ██████╗ ██╗██╗   ██╗ █████╗ ████████╗███████╗    ███╗   ███╗███████╗████████╗██╗  ██╗ ██████╗ ██████╗ ███████╗
// ██╔══██╗██╔══██╗██║██║   ██║██╔══██╗╚══██╔══╝██╔════╝    ████╗ ████║██╔════╝╚══██╔══╝██║  ██║██╔═══██╗██╔══██╗██╔════╝
// ██████╔╝██████╔╝██║██║   ██║███████║   ██║   █████╗      ██╔████╔██║█████╗     ██║   ███████║██║   ██║██║  ██║███████╗
// ██╔═══╝ ██╔══██╗██║╚██╗ ██╔╝██╔══██║   ██║   ██╔══╝      ██║╚██╔╝██║██╔══╝     ██║   ██╔══██║██║   ██║██║  ██║╚════██║
// ██║     ██║  ██║██║ ╚████╔╝ ██║  ██║   ██║   ███████╗    ██║ ╚═╝ ██║███████╗   ██║   ██║  ██║╚██████╔╝██████╔╝███████║
// ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚═╝  ╚═╝   ╚═╝   ╚══════╝    ╚═╝     ╚═╝╚══════╝   ╚═╝   ╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚══════╝

    private void publishEventParticipationEvent(EventParticipationDTO event) {
        applicationEventPublisher.publishEvent(event);
    }

    private void publishEventCreationEvent(EventCreatedBrokerDTO event) {
        applicationEventPublisher.publishEvent(event);
    }

    public boolean isOwner(Principal principal, UUID id) {
        Event event = this.eventRepository.findById(id).orElseThrow(NotFoundException::new);
        String authenticatedUid = principal.getName();
        return authenticatedUid.equals(event.getCreator().getUid());
    }

}
