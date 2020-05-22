package com.iit.aiposizi.service;

import com.iit.aiposizi.entity.AdminEntity;
import com.iit.aiposizi.generated.model.LoginRequest;
import com.iit.aiposizi.generated.model.SignupRequest;

public interface AdminService {

    AdminEntity validateCredentials(LoginRequest loginRequest);

    void create(SignupRequest signupRequest);

    boolean existsByUsername(String username);

}
