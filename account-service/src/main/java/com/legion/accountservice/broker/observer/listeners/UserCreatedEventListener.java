package com.legion.accountservice.broker.observer.listeners;

import com.legion.accountservice.broker.broadcast.dtos.sent.UserCreatedObserverDTO;
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
public class UserCreatedEventListener {

    private final KafkaTemplate<String, UserCreatedObserverDTO> kafkaTemplate;
    private final ObservationRegistry observationRegistry;

    @EventListener
    public void handleUserCreationEvent(UserCreatedObserverDTO user) {
        log.info("User Created Event Received, Sending UserCreatedEvent: {}", user);

        try {
            Observation.createNotStarted("user-created-topic", this.observationRegistry).observeChecked(() ->
            {
                CompletableFuture<SendResult<String, UserCreatedObserverDTO>> future =
                    kafkaTemplate.send("userCreatedTopic", user);
                return future.handle((result, throwable) -> CompletableFuture.completedFuture(result));
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error while sending message to Kafka", e);
        }
    }
}
