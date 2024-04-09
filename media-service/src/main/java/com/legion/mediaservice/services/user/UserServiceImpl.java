package com.legion.mediaservice.services.user;

import com.legion.mediaservice.broker.broadcast.dtos.received.UserCreatedDTO;
import com.legion.mediaservice.entities.User;
import com.legion.mediaservice.mappers.UserCreatedEventMapper;
import com.legion.mediaservice.repositories.UserRepository;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserCreatedEventMapper userMapper;
    private final UserRepository userRepository;


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

    @Override
    public Optional<User> getUserByUid(String uid) {
        return userRepository.findByUid(uid);
    }

    @Override
    public void createUser(UserCreatedDTO dto) {
        userRepository.save(userMapper.userDtoToUser(dto));
    }
}
