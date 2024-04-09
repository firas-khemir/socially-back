package com.legion.mediaservice.broker.observer.listeners;

import com.legion.mediaservice.broker.broadcast.dtos.sent.EventCreatedBrokerDTO;
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
public class EventCreatedListener {

    private final KafkaTemplate<String, EventCreatedBrokerDTO> kafkaTemplate;
    private final ObservationRegistry observationRegistry;

    @EventListener
    public void handleEventCreatedEvent(EventCreatedBrokerDTO event) {
        log.info("Reservation Placed Event Received, Sending ReservationPlacedEvent to notificationTopic: {}",
            event.getId());

        try {
            Observation.createNotStarted("event-creation-topic", this.observationRegistry).observeChecked(() ->
            {
                CompletableFuture<SendResult<String, EventCreatedBrokerDTO>> future =
                    kafkaTemplate.send("eventCreatedTopic", event);
                return future.handle((result, throwable) -> CompletableFuture.completedFuture(result));
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error while sending message to Kafka", e);
        }
    }
}
