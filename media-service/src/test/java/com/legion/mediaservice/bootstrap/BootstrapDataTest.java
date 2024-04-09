package com.legion.mediaservice.bootstrap;

import com.legion.mediaservice.repositories.*;
import com.legion.mediaservice.utils.BootstrapData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase
class BootstrapDataTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    EventImageRepository eventImageRepository;
    @Autowired
    PostImageRepository postImageRepository;
    @Autowired
    CategoryRepository categoryRepository;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(postRepository, eventRepository, postImageRepository,
            eventImageRepository, categoryRepository, userRepository);
    }

    @Test
    void TestRun() throws Exception {
        bootstrapData.run(null);

        assertThat(userRepository.count()).isEqualTo(3);
        assertThat(postRepository.count()).isEqualTo(3);
        assertThat(eventRepository.count()).isEqualTo(3);
        assertThat(postImageRepository.count()).isEqualTo(3);
        assertThat(eventImageRepository.count()).isEqualTo(3);
        assertThat(categoryRepository.count()).isEqualTo(4);
    }
}





