package com.legion.feedservice.broker.broadcast.dtos.received;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFollowDTO {

    private String followerUid;

    private String followedUid;

    private Boolean follow;

}
