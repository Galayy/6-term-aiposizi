package com.iit.aiposizi.service;

import com.iit.aiposizi.generated.model.LoginRequest;
import com.iit.aiposizi.generated.model.SignupRequest;
import com.iit.aiposizi.generated.model.Token;

public interface AuthService {

    Token login(LoginRequest loginRequest);

    Token refresh(String refreshToken);

    void signUp(SignupRequest signupRequest);

}
