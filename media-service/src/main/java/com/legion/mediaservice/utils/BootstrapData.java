package com.legion.mediaservice.utils;


import com.legion.mediaservice.entities.Post;
import com.legion.mediaservice.entities.User;
import com.legion.mediaservice.entities.category.Category;
import com.legion.mediaservice.entities.category.CategoryType;
import com.legion.mediaservice.entities.category.SpecificCategoryType;
import com.legion.mediaservice.entities.event.Event;
import com.legion.mediaservice.entities.event.EventType;
import com.legion.mediaservice.entities.image.EventImage;
import com.legion.mediaservice.entities.image.PostImage;
import com.legion.mediaservice.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final PostRepository postRepository;
    private final EventRepository eventRepository;
    private final PostImageRepository postImageRepository;
    private final EventImageRepository eventImageRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
//        loadCategories();
//        loadUsers();
//        loadPostData();
//        loadEventData();
    }

    private void loadPostData() {

//        if (postRepository.count() == 0) {

            // Fetch all users
            User user1 = userRepository.findByUid("11123456").get();
            User user2 = userRepository.findByUid("22234567").get();
            User user3 = userRepository.findByUid("33334567").get();


            Post post1 = Post.builder()
                .content("test-post1")
                .creator(user1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

            Post post2 = Post.builder()
                .content("test-post2")
                .creator(user2)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

            Post post3 = Post.builder()
                .content("test-post3")
                .creator(user3)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();


            PostImage post1PostImage1 = PostImage.builder()
                .hq_url("1-1")
                .mq_url("1-1")
                .lq_url("1-1")
                .post(post1)
                .build();

            PostImage post1PostImage2 = PostImage.builder()
                .hq_url("1-2")
                .mq_url("1-2")
                .lq_url("1-2")
                .post(post1)
                .build();

            PostImage post2PostImage1 = PostImage.builder()
                .hq_url("2-1")
                .mq_url("2-1")
                .lq_url("2-1")
                .post(post2)
                .build();

            postRepository.save(post1);
            postRepository.save(post2);
            postRepository.save(post3);

            postImageRepository.save(post1PostImage1);
            postImageRepository.save(post1PostImage2);
            postImageRepository.save(post2PostImage1);

//        }
    }

    private void loadEventData() {

        if (eventRepository.count() == 0) {

            // Fetch necessary categories for the created listeners
            Category campingCategory = categoryRepository.findById(1L).get();
            Category sportsCategory = categoryRepository.findById(2L).get();
            Category footballCategory = categoryRepository.findById(3L).get();
            Category gamingCategory = categoryRepository.findById(4L).get();

            // Fetch all users
            User user1 = userRepository.findByUid("11123456").get();
            User user2 = userRepository.findByUid("22234567").get();
            User user3 = userRepository.findByUid("33334567").get();


            // Event 1
            // Add listeners 1 dates when it will occur
            LocalDateTime e1d1 = LocalDateTime.of(2023,12,12,6,0);

            // Create listeners 1 categories Set<>
            Set<Category> event1cats = new HashSet<>();
            event1cats.add(campingCategory);


            // Build listeners
            Event event1 = Event.builder()
                .name("camping listeners")
                .details("this is a random listeners")
                .type(EventType.EVENT)
                .capacity(30)
                .startDate(e1d1)
                .duration(2880) // 2 days
                .maxDelay(0)
                .creator(user1)
                .categories(event1cats)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();



            // Event 2
            // Add listeners 2 dates when it will occur
            LocalDateTime e2d1 = LocalDateTime.of(2023,8,28, 20,0);

            // Create listeners 2 categories Set<>
            Set<Category> event2cats = new HashSet<>();
            event2cats.add(footballCategory);
            event2cats.add(sportsCategory);

            // Build listeners
            Event event2 = Event.builder()
                .name("tor7 koura")
                .details("tor7 koura listeners")
                .type(EventType.GATHERING)
                .capacity(20)
                .startDate(e2d1)
                .duration(120) // 2 hours
                .maxDelay(30)
                .creator(user2)
                .categories(event2cats)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();



            // Event 3
            // Add listeners 3 dates when it will occur
            LocalDateTime e3d1 = LocalDateTime.of(2023,8,28, 19, 0);



            // Create listeners 3 categories Set<>
            Set<Category> event3cats = new HashSet<>();
            event3cats.add(gamingCategory);

            // Build listeners
            Event event3 = Event.builder()
                .name("online game")
                .details("online game listeners")
                .type(EventType.GATHERING)
                .capacity(16)
                .startDate(e3d1)
                .duration(120) // 2 hours
                .maxDelay(90)
                .creator(user3)
                .categories(event3cats)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();


            // Event images
            EventImage event1Image1 = EventImage.builder()
                .hq_url("1-1")
                .mq_url("1-1")
                .lq_url("1-1")
                .event(event1)
                .build();

            EventImage event1Image2 = EventImage.builder()
                .hq_url("1-2")
                .mq_url("1-2")
                .lq_url("1-2")
                .event(event1)
                .build();

            EventImage event2Image1 = EventImage.builder()
                .hq_url("2-1")
                .mq_url("2-1")
                .lq_url("2-1")
                .event(event2)
                .build();

            eventRepository.save(event1);
            eventRepository.save(event2);
            eventRepository.save(event3);

            eventImageRepository.save(event1Image1);
            eventImageRepository.save(event1Image2);
            eventImageRepository.save(event2Image1);
        }
    }

    private void loadCategories() {
        if (categoryRepository.count() == 0) {
            Category campingCategory = Category.builder()
                .id(1L)
                .type(CategoryType.ACTIVITIES)
                .specificType(SpecificCategoryType.CAMPING)
                .build();
            categoryRepository.save(campingCategory);

            Category sportsCategory = Category.builder()
                .id(2L)
                .type(CategoryType.SPORTS)
                .build();
            categoryRepository.save(sportsCategory);

            Category footbalCategory = Category.builder()
                .id(3L)
                .type(CategoryType.SPORTS)
                .specificType(SpecificCategoryType.FOOTBALL)
                .build();
            categoryRepository.save(footbalCategory);

            Category gamingCategory = Category.builder()
                .id(4L)
                .type(CategoryType.GAMING)
                .build();
            categoryRepository.save(gamingCategory);
        }
    }

    private void loadUsers() {

        if (userRepository.count() == 0) {

            User user1 = User.builder()
                .uid("11123456")
                .photo("pic_url")
                .username("first_tester_user")
                .isVerified(false)
                .isHotshot(false)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
            userRepository.save(user1);

            User user2 = User.builder()
                .uid("22234567")
                .photo("pic_url2")
                .username("second_tester_user")
                .isVerified(true)
                .isHotshot(false)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
            userRepository.save(user2);

            User user3 = User.builder()
                .uid("33334567")
                .photo("pic_url3")
                .username("tester_user_the_third")
                .isVerified(true)
                .isHotshot(true)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
            userRepository.save(user3);

        }
    }

}
