package com.legion.mediaservice.dto.feed;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Value
@Builder
public class FeedDTO {
    List<GenericFeedSetDTO> feed = new ArrayList<>();

//    Set<RecommendationDTO> recommendationDTOSet = new HashSet<>();
//    Set<StoryDTO> storyDTOSet = new HashSet<>();
//    Page<EventDTO> eventDTOPage;
}
