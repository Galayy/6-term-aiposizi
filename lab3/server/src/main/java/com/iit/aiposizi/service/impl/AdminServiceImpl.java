package com.iit.aiposizi.service.impl;

import com.iit.aiposizi.entity.AdminEntity;
import com.iit.aiposizi.exception.EntityNotFoundException;
import com.iit.aiposizi.exception.IncorrectJwtAuthenticationException;
import com.iit.aiposizi.generated.model.LoginRequest;
import com.iit.aiposizi.generated.model.SignupRequest;
import com.iit.aiposizi.repository.AdminRepository;
import com.iit.aiposizi.security.UserDetailsImpl;
import com.iit.aiposizi.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.iit.aiposizi.mapper.AdminMapper.ADMIN_MAPPER;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService, UserDetailsService {

    private final AdminRepository adminRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public AdminEntity validateCredentials(LoginRequest loginRequest) {
        var admin = adminRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() ->
        {
            throw new EntityNotFoundException(String.format("Admin with username %s doesn't exist",
                    loginRequest.getUsername()));
        });
        if (!passwordEncoder.matches(loginRequest.getPassword(), admin.getPassword())) {
            throw new IncorrectJwtAuthenticationException("Invalid password");
        }
        return admin;
    }

    @Override
    public void create(SignupRequest signupRequest) {
        var encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        var entity = new AdminEntity();
        entity.setPassword(encodedPassword);
        entity.setUsername(signupRequest.getUsername());
        entity.setRole(ADMIN_MAPPER.toEntity(signupRequest.getRole()));
        adminRepository.saveAndFlush(entity);

        log.info("IN create - admin: admin was added to database");
    }

    @Override
    public boolean existsByUsername(String username) {
        return adminRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var entity = adminRepository.findById(UUID.fromString(username)).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with id %s not found", username)));

        var userDetails = new UserDetailsImpl();
        userDetails.setId(entity.getId());
        userDetails.setRole(entity.getRole());
        return userDetails;
    }

}
