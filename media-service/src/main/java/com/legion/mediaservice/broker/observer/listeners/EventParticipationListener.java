package com.legion.mediaservice.broker.observer.listeners;

import com.legion.mediaservice.broker.broadcast.dtos.sent.EventParticipationDTO;
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
public class EventParticipationListener {

    private final KafkaTemplate<String, EventParticipationDTO> kafkaTemplate;
    private final ObservationRegistry observationRegistry;

    @EventListener
    public void handleEventParticipationEvent(EventParticipationDTO event) {
        log.info("Event participation Event Received, Broadcasting EventParticipationDTO: {}",
            event.getEventId());

        try {
            Observation.createNotStarted("event-participation-topic", this.observationRegistry).observeChecked(() ->
            {
                CompletableFuture<SendResult<String, EventParticipationDTO>> future =
                    kafkaTemplate.send("eventParticipationTopic", event);
                return future.handle((result, throwable) -> CompletableFuture.completedFuture(result));
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error while sending message to Kafka", e);
        }
    }
}
