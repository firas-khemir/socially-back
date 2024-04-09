package com.legion.accountservice.admin;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.legion.accountservice.config.Roles;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserManagementService {

    private final FirebaseAuth firebaseAuth;

    public void setUserClaims(String uid, List<Roles> requestedRoles) throws FirebaseAuthException {
        List<String> permissions = requestedRoles
            .stream()
            .map(Enum::toString)
            .toList();

        log.warn(permissions.get(0));

        Map<String, Object> claims = Map.of("custom_claims", permissions);
        firebaseAuth.setCustomUserClaims(uid, claims);
    }
}
