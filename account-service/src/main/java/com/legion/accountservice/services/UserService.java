package com.legion.accountservice.services;

import com.google.firebase.auth.FirebaseAuthException;
import com.legion.accountservice.dto.BaseUserDTO;
import com.legion.accountservice.dto.InitialSocialAuthDTO;
import com.legion.accountservice.dto.SocialAuthDTO;
import com.legion.accountservice.dto.response.UserProfileDTO;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    /** Checks if token is valid in Firebase and returns user and login if found
     * or creates a new user in Firebase (First stage of signup) if not found.
     @param dto The DTO that contains the token id that will be used for verification.
     @return A DTO that contains the user data.
     */
    BaseUserDTO verifyFirebaseTokenId(InitialSocialAuthDTO dto) throws FirebaseAuthException;

    /** Creates a new user after verifying the token on firebase.
     @param dto The DTO that contains the user object.
     @return A DTO that contains the user data.
     */
    BaseUserDTO signupUsingFirebaseToken(SocialAuthDTO dto) throws FirebaseAuthException;


    /** Use firebaseUid to lookup user to login.
     @param uid id of the user in firebase that's stored in the User entity as uid in the DB.
     @return A DTO that contains the user data.
     */
    UserProfileDTO findUserByUid(String uid, Principal principal);
    UserProfileDTO findUserById(UUID id, Principal principal);
    Optional<BaseUserDTO> findUserByUsername(String username);

    Optional<BaseUserDTO> findUserByFirebaseUid(String id);

    Page<BaseUserDTO> getAllUsers(String searchQuery, Integer page, Integer pageSize);

    /** Get the logged-in user using the firebase uid extracted from the security context validated from the Auth header.
     @return A DTO that contains the user data.
     */
    Optional<BaseUserDTO> getCurrentUser();

}
