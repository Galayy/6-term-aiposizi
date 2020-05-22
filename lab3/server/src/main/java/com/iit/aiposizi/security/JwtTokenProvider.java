package com.iit.aiposizi.security;

import com.iit.aiposizi.exception.IncorrectJwtAuthenticationException;
import com.iit.aiposizi.generated.model.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

import static com.iit.aiposizi.entity.enums.AdminRoleEntity.fromAuthority;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static java.util.Base64.getEncoder;
import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.jmx.export.naming.IdentityNamingStrategy.TYPE_KEY;

@Data
@Log4j2
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${app.jwt.secret.key}")
    private String secretKey;

    private static final String ROLE_KEY = "role";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String EXPIRED_OR_INVALID_JWT_TOKEN = "Expired or invalid JWT token";

    private static final String ACCESS_TOKEN = "access";
    private static final String REFRESH_TOKEN = "refresh";

    private static final int TOKEN_EXPIRATION_TIME = 3_600_000;
    private static final int REFRESH_TOKEN_EXPIRATION_TIME = 360_000_000;
    private static final int WAIT_TIME_MILLIS = 1000;


    @PostConstruct
    protected void init() {
        secretKey = getEncoder().encodeToString(secretKey.getBytes());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public Token refreshTokens(String refreshToken) {
        var authentication = getAuthentication(refreshToken);
        if (authentication == null) {
            throw new IllegalStateException();
        }

        return createToken((UserDetails) authentication);
    }

    public Token createToken(final UserDetails userDetails) {
        var currentTimeMillis = System.currentTimeMillis();
        var issuedAt = new Date(currentTimeMillis);
        var accessExpiresIn = new Date(currentTimeMillis + TOKEN_EXPIRATION_TIME);
        var refreshExpiresIn = new Date(currentTimeMillis + REFRESH_TOKEN_EXPIRATION_TIME);
        var userDetailsImpl = (UserDetailsImpl) userDetails;

        var accessToken = createToken(userDetailsImpl, issuedAt, accessExpiresIn, ACCESS_TOKEN);
        var refreshToken = createToken(userDetailsImpl, issuedAt, refreshExpiresIn, REFRESH_TOKEN);
        return new Token()
                .issuedAt(issuedAt.getTime())
                .accessToken(accessToken)
                .accessExpiresIn(accessExpiresIn.getTime())
                .refreshToken(refreshToken)
                .refreshExpiresIn(refreshExpiresIn.getTime());
    }

    private String createToken(final UserDetailsImpl userDetails,
                               final Date issuedAt,
                               final Date expiresIn,
                               final String tokenType) {
        return Jwts.builder()
                .setSubject(userDetails.getId().toString())
                .setIssuedAt(issuedAt)
                .setExpiration(expiresIn)
                .claim(TYPE_KEY, tokenType)
                .claim(ROLE_KEY, userDetails.getRole().toString())
                .signWith(HS256, secretKey)
                .compact();
    }

    UsernamePasswordAuthenticationToken getAuthentication(String token) {
        var claims = getClaims(token);
        var role = fromAuthority(claims.get(ROLE_KEY).toString());
        var username = getUsername(token);

        var userDetails = new UserDetailsImpl();
        userDetails.setRole(role);
        userDetails.setId(UUID.fromString(username));
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

    String resolveToken(HttpServletRequest request) {
        var bearerToken = request.getHeader(AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    boolean validateToken(String token) {
        var claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return !claims.getBody().getExpiration().before(new Date());
    }

    private String getUsername(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        } catch (JwtException | IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            throw new IncorrectJwtAuthenticationException(EXPIRED_OR_INVALID_JWT_TOKEN);
        }
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (JwtException e) {
            throw new IncorrectJwtAuthenticationException(EXPIRED_OR_INVALID_JWT_TOKEN);
        }
    }

}
