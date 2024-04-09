package com.legion.accountservice.broker.observer.listeners;

import com.legion.accountservice.broker.broadcast.dtos.sent.UserCreatedObserverDTO;
import com.legion.accountservice.broker.broadcast.dtos.sent.UserFollowDTO;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserFollowEventListener {

    private final KafkaTemplate<String, UserFollowDTO> kafkaTemplate;
    private final ObservationRegistry observationRegistry;

    @EventListener
    public void handleUserCreationEvent(UserFollowDTO userFollowDTO) {
        log.info("User Follow Event Received, Sending UserCreatedEvent: {}", userFollowDTO);

        try {
            Observation.createNotStarted("user-follow-topic", this.observationRegistry).observeChecked(() ->
            {
                CompletableFuture<SendResult<String, UserFollowDTO>> future =
                    kafkaTemplate.send("userFollowTopic", userFollowDTO);
                return future.handle((result, throwable) -> CompletableFuture.completedFuture(result));
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error while sending message to Kafka", e);
        }
    }
}
