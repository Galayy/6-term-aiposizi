package com.iit.aiposizi.api;

import com.iit.aiposizi.generated.api.AuthApi;
import com.iit.aiposizi.generated.model.LoginRequest;
import com.iit.aiposizi.generated.model.SignupRequest;
import com.iit.aiposizi.generated.model.Token;
import com.iit.aiposizi.service.AuthService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@Api(tags = "auth")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthApiImpl implements AuthApi {

    private final AuthService authService;

    @Override
    public ResponseEntity<Token> login(@Valid @RequestBody final LoginRequest loginRequest) {
        var token = authService.login(loginRequest);
        return new ResponseEntity<>(token, CREATED);
    }

    @Override
    public ResponseEntity<Token> refresh(@NotNull @Valid @RequestParam final String refreshToken) {
        var token = authService.refresh(refreshToken);
        return new ResponseEntity<>(token, CREATED);
    }

    @Override
    public ResponseEntity<Void> signup(@Valid @RequestBody final SignupRequest signupRequest) {
        authService.signUp(signupRequest);
        return new ResponseEntity<>(CREATED);
    }

}
