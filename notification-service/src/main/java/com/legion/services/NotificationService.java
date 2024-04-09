package com.legion.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.legion.events.ReservationPlacedEvent;
import com.legion.events.TestEvent;
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
public class NotificationService {

    private final ObservationRegistry observationRegistry;
    private final Tracer tracer;

    @KafkaListener(topics = "participateTopic")
    public void handleNotification(ReservationPlacedEvent reservationPlacedEvent) {
        Observation.createNotStarted("on-message", this.observationRegistry).observe(() -> {
            log.info("Got message <{}>", reservationPlacedEvent);
            log.info("TraceId- {}, Received Notification for Event - {} with user - {}",
                this.tracer.currentSpan().context().traceId(),
                reservationPlacedEvent.getEventId(),
                reservationPlacedEvent.getUserId());
        });
    }

    @KafkaListener(groupId = "notificationId", topics = "testTopic")
    public void receivedMessage(TestEvent message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(message);
        log.info("Json message received using Kafka listeners " + jsonString);
    }
}
