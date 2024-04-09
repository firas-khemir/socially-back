package com.legion.mediaservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.legion.mediaservice.entities.event.Event;
import com.legion.mediaservice.mappers.EventMapper;
import com.legion.mediaservice.dto.ImageDTO;
import com.legion.mediaservice.dto.event.CreateEventDTO;
import com.legion.mediaservice.dto.event.EventDTO;
import com.legion.mediaservice.repositories.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.legion.mediaservice.entities.event.EventRecurrenceType.NO_RECURRENCE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
class EventControllerIT {
    @Autowired
    EventController eventController;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    EventMapper eventMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    @Transactional
    void testListEvents() {
        Page<EventDTO> events = eventController.listEvents(null,null, 0, 25);
        assertThat(events.stream().count()).isEqualTo(3);
    }

//    @Test
//    @Transactional
//    void testListCreatorEvents() {
//        Page<EventDTO> listeners = eventController.listEvents(UUID.fromString("a8376bf3-f7d4-4bfc-9077-7d886f4df985"),null, 0, 25);
//        assertThat(listeners.stream().count()).isEqualTo(2);
//    }
//
//    @Test
//    @Transactional
//    void testSearchEvents() {
//        Page<EventDTO> testEvent1 = eventController.listEvents(null,"test-event1", 0, 25);
//        assertThat(testEvent1.stream().count()).isEqualTo(1);
//
//
//        Page<EventDTO> allTestEvents = eventController.listEvents(null,"test-listeners", 0, 25);
//        assertThat(allTestEvents.stream().count()).isEqualTo(3);
//    }
//
//    @Test
//    @Transactional
//    void testListCreatorEventsWithSearch() {
//        Page<EventDTO> listeners = eventController.listEvents(UUID.fromString("a8376bf3-f7d4-4bfc-9077-7d886f4df985"),"test-event2", 0, 25);
//        assertThat(listeners.stream().count()).isEqualTo(1);
//    }

    @Test
    @Transactional
    void testGetEventById() {
        Event event = eventRepository.findAll().get(0);
        EventDTO foundEvent = eventController.getEventById(event.getId());
        assertThat(foundEvent).isNotNull();
    }

    @Rollback
    @Transactional
    @Test
    void saveNewEventTest() {

        ImageDTO imageDTO1 = ImageDTO.builder()
            .hq_url("1")
            .mq_url("1")
            .lq_url("1")
            .build();

        ImageDTO imageDTO2 = ImageDTO.builder()
            .hq_url("2")
            .mq_url("2")
            .lq_url("2")
            .build();

        Set<ImageDTO> imageList = new HashSet<>();

        LocalDateTime startDate1 = LocalDateTime.of(2023, 12 ,12,0,0);

        imageList.add(imageDTO1);
        imageList.add(imageDTO2);

        CreateEventDTO eventDTO = CreateEventDTO.builder()
                .name("test-event1")
                .details("test-event1")
                .images(imageList)
                .startDate(startDate1)
                .duration(360)
                .recurrenceType(NO_RECURRENCE)
                .build();


        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("me");


        ResponseEntity<?> responseEntity = eventController.createNewEvent(eventDTO, mockPrincipal);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");

        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Event event = eventRepository.findById(savedUUID).get();
        assertThat(event).isNotNull();
    }

    @Test
    void testDeleteByIDNotFound() {
        assertThrows(NotFoundException.class, () -> {
            eventController.deleteEvent(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void deleteByIdFound() {
        Event event = eventRepository.findAll().get(0);

        ResponseEntity<?> responseEntity = eventController.deleteEvent(event.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertThat(eventRepository.findById(event.getId()).isEmpty()).isTrue();
    }


    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, () -> {
          eventController.updateEventByID(UUID.randomUUID(), EventDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void updateExistingEvent() {

        Event event = eventRepository.findAll().get(0);

        EventDTO eventDTO = eventMapper.eventToEventDTO(event);
        final String eventContent = "listeners-1-listeners-test";
//        eventDTO.setContent(eventContent);

        ResponseEntity<?> responseEntity = eventController.updateEventByID(event.getId(), eventDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Event updatedEvent = eventRepository.findById(event.getId()).get();
        assertThat(updatedEvent.getDetails()).isEqualTo(eventContent);
    }
}







