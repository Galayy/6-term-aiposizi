package com.iit.aiposizi.service.impl;

import com.iit.aiposizi.exception.EntityAlreadyProcessedException;
import com.iit.aiposizi.generated.model.LoginRequest;
import com.iit.aiposizi.generated.model.SignupRequest;
import com.iit.aiposizi.generated.model.Token;
import com.iit.aiposizi.security.JwtTokenProvider;
import com.iit.aiposizi.security.UserDetailsImpl;
import com.iit.aiposizi.service.AdminService;
import com.iit.aiposizi.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import static com.iit.aiposizi.util.SecurityUtils.getCurrentUser;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AdminService adminService;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Token login(final LoginRequest loginRequest) {
        var admin = adminService.validateCredentials(loginRequest);

        var authenticationToken = new UsernamePasswordAuthenticationToken(admin.getId(), admin.getRole());
        var userDetails = new UserDetailsImpl();
        userDetails.setId(admin.getId());
        userDetails.setRole(admin.getRole());

        return jwtTokenProvider.createToken(userDetails);
    }

    @Override
    public Token refresh(final String refreshToken) {
        return jwtTokenProvider.refreshTokens(refreshToken);
    }

    @Override
    public void signUp(final SignupRequest signupRequest) {
        isSignedIn();
        isAlreadyExists(signupRequest.getUsername());

        log.info("IN signUp - auth: sign up success");
        adminService.create(signupRequest);
    }

    private void isSignedIn() {
        if (getCurrentUser() != null) {
            throw new EntityAlreadyProcessedException("You can't sign up until logout");
        }
    }

    private void isAlreadyExists(final String username) {
        if (adminService.existsByUsername(username)) {
            throw new EntityAlreadyProcessedException(String.format("Admin with username %s already exists", username));
        }
    }

}
