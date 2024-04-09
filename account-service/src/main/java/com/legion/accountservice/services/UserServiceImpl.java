package com.legion.accountservice.services;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.legion.accountservice.broker.broadcast.dtos.sent.UserCreatedObserverDTO;
import com.legion.accountservice.dto.BaseUserDTO;
import com.legion.accountservice.dto.InitialSocialAuthDTO;
import com.legion.accountservice.dto.SocialAuthDTO;
import com.legion.accountservice.dto.response.UserProfileDTO;
import com.legion.accountservice.entities.User;
import com.legion.accountservice.exceptions.NotFoundException;
import com.legion.accountservice.mappers.BaseUserDTOMapper;
import com.legion.accountservice.repository.UserRepository;
import com.legion.accountservice.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final FirebaseAuth firebaseAuth;

    private final BaseUserDTOMapper baseUserMapper;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public BaseUserDTO verifyFirebaseTokenId(InitialSocialAuthDTO dto) throws FirebaseAuthException {
        FirebaseToken decodedToken = firebaseAuth.verifyIdToken(dto.getTokenId());
        if (decodedToken != null) {
            String uid = decodedToken.getUid();
            log.warn(uid);
            return findUserByFirebaseUid(uid).orElse(null);
        }
        throw new RuntimeException();
    }

    @Override
    public BaseUserDTO signupUsingFirebaseToken(SocialAuthDTO dto) throws FirebaseAuthException {
        FirebaseToken decodedToken = firebaseAuth.verifyIdToken(dto.getTokenId());
        if (decodedToken != null) {
            String uid = decodedToken.getUid();
            BaseUserDTO baseUser = findUserByFirebaseUid(uid).orElse(null);

            if (baseUser != null)
                return baseUser;
            else {
                dto.setUid(uid);
                BaseUserDTO user = saveUserToDB(dto);
                UserCreatedObserverDTO event = new UserCreatedObserverDTO(user.getId(), user.getUid(), user.getUsername(),
                    user.getPhoto(), true, true, user.getCreatedDate(),
                    user.getLastModifiedDate());
                publishUserCreatedEvent(event);
                return user;
            }
        }
        throw new RuntimeException("Couldn't decode token");
    }

    @Override
    public UserProfileDTO findUserById(UUID id, Principal principal) {

        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);

        // Check if the connected user is following the looked up user
        Boolean isFollowing = user.getFollowers().stream().map(User::getUid).anyMatch(principal.getName()::equals);
        // Check if looked up user is following the connected user
        Boolean isFollower = user.getFollowing().stream().map(User::getUid).anyMatch(principal.getName()::equals);

        UserProfileDTO.UserProfileDTOBuilder userProfileDTOBuilder = UserProfileDTO.builder();
        userProfileDTOBuilder.id(user.getId());
        userProfileDTOBuilder.uid(user.getUid());
        userProfileDTOBuilder.username(user.getUsername());
        userProfileDTOBuilder.email(user.getEmail());
        userProfileDTOBuilder.photo(user.getPhoto());
        userProfileDTOBuilder.birthdate(user.getBirthdate());
        userProfileDTOBuilder.followersCount(user.getFollowers().size());
        userProfileDTOBuilder.followingCount(user.getFollowing().size());
        userProfileDTOBuilder.isFollower(isFollower);
        userProfileDTOBuilder.isFollowing(isFollowing);
        userProfileDTOBuilder.createdDate(user.getCreatedDate());
        return userProfileDTOBuilder.build();
    }

    @Override
    public UserProfileDTO findUserByUid(String uid, Principal principal) {

        User user = userRepository.findByUid(uid).orElseThrow(NotFoundException::new);

        // Check if the connected user is following the looked up user
        Boolean isFollowing = user.getFollowers().stream().map(User::getUid).anyMatch(principal.getName()::equals);
        // Check if looked up user is following the connected user
        Boolean isFollower = user.getFollowing().stream().map(User::getUid).anyMatch(principal.getName()::equals);

        UserProfileDTO.UserProfileDTOBuilder userProfileDTOBuilder = UserProfileDTO.builder();
        userProfileDTOBuilder.id(user.getId());
        userProfileDTOBuilder.uid(user.getUid());
        userProfileDTOBuilder.username(user.getUsername());
        userProfileDTOBuilder.email(user.getEmail());
        userProfileDTOBuilder.photo(user.getPhoto());
        userProfileDTOBuilder.birthdate(user.getBirthdate());
        userProfileDTOBuilder.followersCount(user.getFollowers().size());
        userProfileDTOBuilder.followingCount(user.getFollowing().size());
        userProfileDTOBuilder.isFollower(isFollower);
        userProfileDTOBuilder.isFollowing(isFollowing);
        userProfileDTOBuilder.createdDate(user.getCreatedDate());
        return userProfileDTOBuilder.build();

    }

    @Override
    public Optional<BaseUserDTO> findUserByUsername(String username) {
        return Optional.ofNullable(baseUserMapper.userToBaseUserDTO(userRepository.findByUsername(username).orElse(null)));
    }

    @Override
    public Optional<BaseUserDTO> findUserByFirebaseUid(String uid) {
        return Optional.ofNullable(baseUserMapper.userToBaseUserDTO(userRepository.findByUid(uid).orElse(null)));
    }

    @Override
    public Page<BaseUserDTO> getAllUsers(String searchQuery, Integer page, Integer pageSize) {
        PageRequest pageRequest = new Utils().buildPageRequest(page, pageSize);
        Page<User> userPage;

        if (StringUtils.hasText(searchQuery))
            userPage = userRepository.findByDisplayNameContainingOrUsernameContaining(searchQuery, searchQuery, pageRequest);
        else
            userPage = userRepository.findAll(pageRequest);

        return userPage.map(baseUserMapper::userToBaseUserDTO);
    }

    @Override
    public Optional<BaseUserDTO> getCurrentUser() {
        String uid = SecurityContextHolder.getContext().getAuthentication().getName();
        return findUserByFirebaseUid(uid);
    }

    private BaseUserDTO saveUserToDB(BaseUserDTO dto) {
        return baseUserMapper.userToBaseUserDTO(userRepository.save(baseUserMapper.baseUserDTOToUser(dto)));
    }

    public void publishUserCreatedEvent(UserCreatedObserverDTO event) {
        applicationEventPublisher.publishEvent(event);
    }

}
