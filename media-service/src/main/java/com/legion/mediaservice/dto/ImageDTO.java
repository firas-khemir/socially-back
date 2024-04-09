package com.legion.mediaservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Builder
@Data
public class ImageDTO {

    private UUID id;

    private Long version;

    @NotBlank()
    private String hq_url;
    @NotBlank()
    private String mq_url;
    @NotBlank()
    private String lq_url;

    private List<TagDto> tags;

}
