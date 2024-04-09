package com.legion.accountservice.controllers;

import com.google.firebase.auth.FirebaseAuthException;
import com.legion.accountservice.dto.BaseUserDTO;
import com.legion.accountservice.dto.InitialSocialAuthDTO;
import com.legion.accountservice.dto.SocialAuthDTO;
import com.legion.accountservice.services.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account/auth")
@Slf4j
@Validated
public class AuthController {

    private final UserServiceImpl userService;

    @PostMapping("/verify-token")
    public ResponseEntity<?> verifyFirebaseTokenId(@Valid @RequestBody InitialSocialAuthDTO dto) throws FirebaseAuthException {

        log.warn(dto.getTokenId());

        BaseUserDTO baseUser = userService.verifyFirebaseTokenId(dto);
        if (baseUser != null) {
            return ResponseEntity.status(HttpStatus.OK).body(baseUser);
        }
        else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PostMapping("/continue-signup")
    public ResponseEntity<?> signupUsingFirebaseToken(@Valid @RequestBody SocialAuthDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.signupUsingFirebaseToken(dto));
        } catch (Exception e){
//            Parse errors correctly
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCause().getCause().getLocalizedMessage());
        }
    }

}
