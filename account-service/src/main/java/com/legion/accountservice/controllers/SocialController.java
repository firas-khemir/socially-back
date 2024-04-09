package com.legion.accountservice.controllers;

import com.legion.accountservice.exceptions.NotFoundException;
import com.legion.accountservice.services.SocialService;
import com.legion.accountservice.services.SocialServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping
@Slf4j
public class SocialController {

    public static final String API_PATH = "/api/account/social";
    public static final String API_PATH_ID = API_PATH + "/{userId}";

    private final SocialServiceImpl socialService;

    @PutMapping(value = API_PATH_ID + "/follow")
    public ResponseEntity<?> followUser(@PathVariable("userId") UUID id, Principal principal){
        Boolean result = socialService.followUser(id, principal);

        return  result ?
            new ResponseEntity<>("Successfully followed user " + id, HttpStatus.OK):
            new ResponseEntity<>("Couldn't follow user " + id, HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = API_PATH_ID + "/unfollow")
    public ResponseEntity<?> unfollowUser(@PathVariable("userId") UUID id, Principal principal){
        Boolean result = socialService.unfollowUser(id, principal);

        return  result ?
            new ResponseEntity<>("Successfully followed user " + id, HttpStatus.OK):
            new ResponseEntity<>("Couldn't follow user " + id, HttpStatus.BAD_REQUEST);
    }


}
