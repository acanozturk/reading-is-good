package com.rig.authenticationservice.security.jwt;

import com.rig.authenticationservice.security.userdetails.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Setter
@Slf4j
public final class JwtManager {

    @Value("${app.security.jwt.expiration-ms}")
    private int expirationMs;

    @Value("${app.security.jwt.secret}")
    private String secret;

    public String generateJwt(final Authentication authentication) {
        final UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + expirationMs))
                .id(UUID.randomUUID().toString())
                .subject(userPrincipal.getUsername())
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

}
