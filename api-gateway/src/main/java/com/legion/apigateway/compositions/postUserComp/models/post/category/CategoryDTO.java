package com.legion.apigateway.compositions.postUserComp.models.post.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {

    private UUID id;

    private Long version;

    private String type;
    private String specificType;

}
