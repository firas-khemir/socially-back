package com.legion.accountservice.services;

import com.legion.accountservice.broker.broadcast.dtos.sent.UserFollowDTO;
import com.legion.accountservice.dto.BaseUserDTO;
import com.legion.accountservice.entities.User;
import com.legion.accountservice.exceptions.NotFoundException;
import com.legion.accountservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocialServiceImpl implements SocialService {

    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Boolean followUser(UUID id, Principal principal) {

        try {
            String cUid = principal.getName();

            User cUser = this.userRepository.findByUid(cUid).orElseThrow(NotFoundException::new);
            User followedUser = this.userRepository.findById(id).orElseThrow(NotFoundException::new);

            Set<User> cUserFollowings = new HashSet<>();
            if(cUser.getFollowing() != null) {
                cUserFollowings = cUser.getFollowing();
            }
            cUserFollowings.add(followedUser);
            cUser.setFollowing(cUserFollowings);

            Set<User> followedUserFollowers = new HashSet<>();
            if(followedUser.getFollowers() != null) {
                followedUserFollowers = followedUser.getFollowers();
            }
            followedUserFollowers.add(cUser);
            followedUser.setFollowers(followedUserFollowers);

            userRepository.save(cUser);
            userRepository.save(followedUser);

            UserFollowDTO event = new UserFollowDTO(cUser.getUid(), followedUser.getUid(), true);
            applicationEventPublisher.publishEvent(event);

            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean unfollowUser(UUID id, Principal principal) {
        try {
            String cUid = principal.getName();

            User cUser = this.userRepository.findByUid(cUid).orElseThrow(NotFoundException::new);
            User followedUser = this.userRepository.findById(id).orElseThrow(NotFoundException::new);

            cUser.getFollowing().remove(followedUser);
            followedUser.getFollowers().remove(cUser);

            userRepository.save(cUser);
            userRepository.save(followedUser);

            // UserFollowDTO can be used for both follow and unfollow events
            UserFollowDTO event = new UserFollowDTO(cUser.getUid(), followedUser.getUid(), false);
            applicationEventPublisher.publishEvent(event);

            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public Set<BaseUserDTO> getFollowedUsers(Principal principal) {
        return null;
    }

    @Override
    public Set<BaseUserDTO> getFollowingUsers(Principal principal) {
        return null;
    }
}
