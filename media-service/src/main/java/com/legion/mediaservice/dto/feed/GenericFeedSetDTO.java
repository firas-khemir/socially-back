package com.legion.mediaservice.dto.feed;


import com.legion.mediaservice.dto.feed.partials.FeedDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Value
@Builder
public class GenericFeedSetDTO {
    String dtoType;
    Set<FeedDTO> feedDTOSet = new HashSet<>();
}
