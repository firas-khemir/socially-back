package com.legion.mediaservice.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Data
public class PostDTO {

    private UUID id;

    private Long version;

    @NotBlank()
    private String title;

    @NotBlank()
    private String content;

    private List<ImageDTO> images;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
}
