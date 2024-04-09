package com.legion.apigateway.compositions.postUserComp.models.post;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
