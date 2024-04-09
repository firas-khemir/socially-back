package com.legion.mediaservice.dto.feed.partials;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@AllArgsConstructor
@Value
@Builder
public class RecommendationDTO implements FeedDTO {

    String dtoType = "Recommendation";

    UUID id;

    Long version;

    String title;

    String image;

}
