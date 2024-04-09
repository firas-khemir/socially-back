package com.legion.apigateway.compositions.postUserComp.models.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagDto {
    private UUID id;

    private Long version;

    private String name;
}
