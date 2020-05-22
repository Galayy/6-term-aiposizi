package com.iit.aiposizi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] WHITE_LIST = {
            "/api/v1/addresses/all/**", "/api/v1/offices/all/**", "/api/v1/employees/all/**", "/api/v1/places/all/**",
            "/api/v1/rooms/all/**", "/api/v1/auth/**", "/v2/api-docs", "/configuration/**", "/swagger*/**",
            "/webjars/**", "/"
    };

    private final UserDetailsService userDetailsService;

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) ->
                response.sendError(SC_UNAUTHORIZED, "Unauthorized");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable().csrf().disable().authorizeRequests()
                .antMatchers(WHITE_LIST).permitAll()
                .anyRequest().authenticated().and()
                .sessionManagement().sessionCreationPolicy(STATELESS).and()
                .apply(new JwtConfigurer(jwtTokenProvider));
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
        http.logout().clearAuthentication(true).invalidateHttpSession(true);
        http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(jwtTokenProvider.passwordEncoder());
    }

}
