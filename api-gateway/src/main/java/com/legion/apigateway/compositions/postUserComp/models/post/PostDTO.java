package com.legion.apigateway.compositions.postUserComp.models.post;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {

    private UUID id;

    private Long version;

    @NotBlank()
    private String title;

    @NotBlank()
    private String content;

    private UUID creatorId;

    private List<ImageDTO> images;

//    private List<SubCategoryDTO> subCategories;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
}
