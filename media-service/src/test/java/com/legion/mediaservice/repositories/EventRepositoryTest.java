package com.legion.mediaservice.repositories;

import com.legion.mediaservice.entities.Post;
import com.legion.mediaservice.entities.User;
import com.legion.mediaservice.entities.event.Event;
import com.legion.mediaservice.utils.BootstrapData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@DataJpaTest
@Import({BootstrapData.class})
class EventRepositoryTest {

    @Autowired
    EventRepository eventRepository;

    @Test
    void testSaveEvent() {

        User creator = mock(User.class);

        Event savedEvent = Event.builder()
            .id(UUID.randomUUID())
            .details("test-post1")
            .creator(creator)
            .createdDate(LocalDateTime.now())
            .lastModifiedDate(LocalDateTime.now())
            .build();

        eventRepository.flush();

        assertThat(savedEvent).isNotNull();
        assertThat(savedEvent.getId()).isNotNull();
    }
}
