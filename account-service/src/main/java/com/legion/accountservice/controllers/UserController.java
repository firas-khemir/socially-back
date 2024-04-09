package com.legion.accountservice.controllers;

import com.legion.accountservice.dto.BaseUserDTO;
import com.legion.accountservice.dto.response.UserProfileDTO;
import com.legion.accountservice.exceptions.NotFoundException;
import com.legion.accountservice.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account/users")
@Slf4j
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/find-all")
//    @PreAuthorize("permitAll()")
    public Page<BaseUserDTO> findAllUsers(
        @RequestParam(required = false) String searchQuery,
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer pageSize
    ) {
        return userService.getAllUsers(searchQuery, page, pageSize);
    }

    @GetMapping("/find-by-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserProfileDTO findById(@PathVariable("id") UUID id, Principal principal) {
        return userService.findUserById(id, principal);
    }

    @GetMapping("/find-by-uid/{uid}")
    @ResponseStatus(HttpStatus.OK)
    public UserProfileDTO findById(@PathVariable("uid") String uid, Principal principal) {
        return userService.findUserByUid(uid, principal);
    }

    @GetMapping("/find-by-username")
    @ResponseStatus(HttpStatus.OK)
    public BaseUserDTO findByUsername(@RequestParam String username) {
        return userService.findUserByUsername(username).orElseThrow(NotFoundException::new);
    }

    @GetMapping("/current")
    public Optional<BaseUserDTO> getCurrent() {
        return userService.getCurrentUser();
    }

    @GetMapping(path = "/test")
    @PreAuthorize("{hasAuthority('READ')}")
    public String test(Principal principal) {
        return principal.getName();
    }
}
