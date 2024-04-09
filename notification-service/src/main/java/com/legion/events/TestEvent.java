package com.legion.events;

import lombok.*;
import org.springframework.context.ApplicationEvent;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestEvent {
    private String message;
}
