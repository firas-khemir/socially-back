package com.legion.apigateway.compositions.postUserComp.controllers;

import com.legion.apigateway.compositions.postUserComp.models.PostWithUserDTO;
import com.legion.apigateway.compositions.postUserComp.models.post.PostDTO;
import com.legion.apigateway.compositions.postUserComp.models.user.BaseUserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostUserController {

    private final WebClient.Builder webClientBuilder;

    @GetMapping("api/media/posts/{postId}")
    public Mono<PostWithUserDTO> getPostWithUser(@PathVariable UUID postId, ServerHttpRequest request) {

        // Extract the Authorization header from the incoming request
        String authorizationHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // Add the Authorization header to the WebClient's default headers
        WebClient client = webClientBuilder.defaultHeader(HttpHeaders.AUTHORIZATION, authorizationHeader).build();

        Mono<PostDTO> postMono = retrievePost(postId, client)
            .onErrorResume(WebClientResponseException.class, Mono::error);

        Mono<BaseUserDTO> userMono = postMono
            .flatMap(post -> retrieveUserById(post.getCreatorId(), client)
                .onErrorResume(WebClientResponseException.class, ex -> {
                if (ex.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                    return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));
                } else {
                    return Mono.error(ex);
                }
            }));

        return  userMono.zipWith(postMono, PostWithUserDTO::new);
    }

    private Mono<PostDTO> retrievePost(UUID postId, WebClient client) {
        return client.get()
            .uri("lb://media-service/api/media/posts/"+postId)
            .retrieve()
            .bodyToMono(PostDTO.class)
            .onErrorResume(WebClientResponseException.class, Mono::error);
    }

    private Mono<BaseUserDTO> retrieveUserById(UUID userUid, WebClient client) {
        return client.get()
            .uri("lb://account-service/api/account/users/find-by-id/"+ userUid)
            .retrieve()
            .bodyToMono(BaseUserDTO.class)
            .onErrorResume(WebClientResponseException.class, ex -> {
                if (ex.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                    log.debug(ex.getResponseBodyAsString());
                    return Mono.error(ex);
                } else {
                    return Mono.error(ex);
                }
            });
    }

}
