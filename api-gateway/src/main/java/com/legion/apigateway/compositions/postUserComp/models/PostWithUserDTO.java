package com.legion.apigateway.compositions.postUserComp.models;

import com.legion.apigateway.compositions.postUserComp.models.post.PostDTO;
import com.legion.apigateway.compositions.postUserComp.models.user.BaseUserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostWithUserDTO {

    private BaseUserDTO user;

    private PostDTO postDTO;

}
