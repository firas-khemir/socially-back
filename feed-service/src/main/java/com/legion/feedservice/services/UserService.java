package com.legion.feedservice.services;

import com.legion.feedservice.broker.broadcast.dtos.received.UserCreatedDTO;
import com.legion.feedservice.broker.broadcast.dtos.received.UserFollowDTO;
import com.legion.feedservice.entities.user.FollowRelationship;
import com.legion.feedservice.entities.user.User;
import com.legion.feedservice.mappers.UserMapper;
import com.legion.feedservice.repositories.UserRepository;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;


    private final ObservationRegistry observationRegistry;
    private final Tracer tracer;


    @KafkaListener(topics = "userCreatedTopic")
    public void receivedMessage(UserCreatedDTO message)  {
        Observation.createNotStarted("on-message", this.observationRegistry).observe(() -> {
            log.info("Got message <{}>", message);
            log.info("TraceId- {}, Received Notification for Event - {} with user - {}",
                this.tracer.currentSpan().context().traceId(),
                message.getId(),
                message.getUid());

            createUser(message);
        });
    }

//    @Override
    public void createUser(UserCreatedDTO dto) {
        userRepository.save(userMapper.userCreatedDtoToUser(dto));
    }

    @KafkaListener(topics = "userFollowTopic")
    public void handleUserFollowTopic(UserFollowDTO dto) {
        if (dto.getFollow()) followUser(dto);
        else {
            try {
                unfollowUser(dto);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void followUser(UserFollowDTO dto) {

        User principalUser = userRepository.findByUid(dto.getFollowerUid())
            .orElseThrow(() -> new RuntimeException("User not found"));

        User followedUser = userRepository.findByUid(dto.getFollowedUid())
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (principalUser.getFollowing().stream().noneMatch(rel -> rel.getUser().equals(followedUser))) {
            FollowRelationship followsRelationship = new FollowRelationship();
            followsRelationship.setUser(followedUser);
            followsRelationship.setInteractionCounter(0);

            principalUser.getFollowing().add(followsRelationship);
            userRepository.save(principalUser);
        }
    }

    public void unfollowUser(UserFollowDTO dto) throws Exception {
        userRepository.findByUid(dto.getFollowerUid())
            .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.findByUid(dto.getFollowedUid())
            .orElseThrow(() -> new RuntimeException("User not found"));

        Boolean userDeleted = userRepository.unfollowUser(dto.getFollowerUid(), dto.getFollowedUid());

        if(!userDeleted) throw new Exception("Couldn't unfollow user");

//        principalUser.getFollowing().removeIf(rel -> rel.getUser().getUid().equals(unfollowedUser.getUid()));
//        userRepository.save(principalUser);
    }
}

