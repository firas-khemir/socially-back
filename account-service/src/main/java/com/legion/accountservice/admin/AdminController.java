package com.legion.accountservice.admin;

import com.google.firebase.auth.FirebaseAuthException;
import com.legion.accountservice.config.Roles;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final UserManagementService userManagementService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/hello")
    public String hello() {
        log.warn("hello");
        return "hello";
    }

    @Secured("ROLE_ANONYMOUS")
    @PostMapping("/user-claims/{uid}")
    public void setUserClaims(
        @PathVariable String uid
//        @RequestBody List<Roles> requestedClaims
    ) throws FirebaseAuthException {
        userManagementService.setUserClaims(uid, List.of(Roles.ADMIN));
    }

}
