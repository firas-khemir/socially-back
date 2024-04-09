package com.legion.mediaservice.dto.feed.partials;


import com.legion.mediaservice.dto.UserDTO;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@Value
@Builder
public class StoryDTO implements FeedDTO {

    String dtoType = "Story";

    UUID id;

    Long version;

    UserDTO creator;

    @Override
    public String getDtoType() {
        return dtoType;
    }

}
