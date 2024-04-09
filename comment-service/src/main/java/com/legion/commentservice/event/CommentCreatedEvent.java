package com.legion.commentservice.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;


@Getter
@Setter
public class CommentCreatedEvent extends ApplicationEvent {
    private UUID postId;
    private UUID userId;

    public CommentCreatedEvent(Object source, UUID postId, UUID userId) {
        super(source);
        this.postId = postId;
        this.userId = userId;
    }

    public CommentCreatedEvent(UUID postId, UUID userId) {
        super(postId);
        this.postId = postId;
        this.userId = userId;
    }
}
