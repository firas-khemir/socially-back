package com.legion.accountservice.services;

import com.google.firebase.auth.FirebaseAuthException;
import com.legion.accountservice.dto.BaseUserDTO;
import com.legion.accountservice.dto.InitialSocialAuthDTO;
import com.legion.accountservice.dto.SocialAuthDTO;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface SocialService {

    /** Specified user gets followed by the currently connected user .
     @param id The id of the user that will be followed.
     @return Boolean to establish the result of the operation.
     */
    Boolean followUser(UUID id, Principal principal);

    /** Specified user gets followed by the currently connected user .
     @param id The id of the user that will be followed.
     @return Boolean to establish the result of the operation.
     */
    Boolean unfollowUser(UUID id, Principal principal);

    /** Get the list of users that are followed by the current user.
     @return Set of users.
     */
    Set<BaseUserDTO> getFollowedUsers(Principal principal);

    /** Get the list of users that are following the current user.
     @return Set of users.
     */
    Set<BaseUserDTO> getFollowingUsers(Principal principal);

}
