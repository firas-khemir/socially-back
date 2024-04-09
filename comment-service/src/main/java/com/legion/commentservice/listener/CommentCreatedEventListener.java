package com.legion.commentservice.listener;

import com.legion.commentservice.event.CommentCreatedEvent;
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
public class CommentCreatedEventListener {

    private final KafkaTemplate<String, CommentCreatedEvent> kafkaTemplate;
    private final ObservationRegistry observationRegistry;

    final String topicName = "commentCreatedTopic";
    final String observationName = "comment-created-topic";

    @EventListener
    public void handleCommentCreatedEvent(CommentCreatedEvent event) {
        log.info("Comment created Event Received, Sending to commentCreatedTopic: {}",
            event.getPostId());

        // Create Observation for Kafka Template
        try {
            Observation.createNotStarted(observationName, this.observationRegistry).observeChecked(() ->
            {
                CompletableFuture<SendResult<String, CommentCreatedEvent>> future = kafkaTemplate.send(topicName,
                        new CommentCreatedEvent(event.getPostId(), event.getUserId()));
                return future.handle((result, throwable) -> CompletableFuture.completedFuture(result));
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error while sending message to Kafka", e);
        }
    }
}
