package com.rig.apigateway.security;

import com.rig.apigateway.util.TracingConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static com.rig.apigateway.config.WebSecurityConfig.JWT_PUBLIC_WHITELIST;

@Component
@Setter
@Slf4j
public final class JwtFilter extends OncePerRequestFilter {

    @Value("${app.security.jwt.secret}")
    private String secret;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {
        try {
            final String jwt = parseJwt(request);

            if (jwt == null) {
                throw new MalformedJwtException("Jwt is null.");
            }

            final Jws<Claims> claimsJws = validateJwt(jwt);
            final String userCode = claimsJws.getPayload().getSubject();
            
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userCode,
                    null,
                    null
            );

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            
            MDC.put(TracingConstant.X_USER_CODE, userCode);
        } catch (Exception e) {
            log.error("Cannot set user authentication:", e);
        }

        filterChain.doFilter(request, response);
    }

    public String parseJwt(final HttpServletRequest request) {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        return StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")
                ? authorizationHeader.substring(7)
                : null;
    }

    public Jws<Claims> validateJwt(final String jwt) {
        try {
            return Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseSignedClaims(jwt);
        } catch (Exception e) {
            log.error("Invalid JWT token received.");
            throw e;
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return Arrays.stream(JWT_PUBLIC_WHITELIST).anyMatch(url -> request.getRequestURI().startsWith(url));
    }
    
}
