package com.rig.authenticationservice.service;

import com.rig.authenticationservice.data.payload.request.LoginRequest;
import com.rig.authenticationservice.data.payload.response.JwtResponse;
import com.rig.authenticationservice.security.jwt.JwtManager;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class AuthenticationServiceImpl implements AuthenticationService {
    
    private final AuthenticationManager authenticationManager;
    
    private final JwtManager jwtManager;
    
    @Override
    public JwtResponse login(final LoginRequest loginRequest) {
        final Authentication authentication = prepareAuthentication(loginRequest);
        final String jwt = jwtManager.generateJwt(authentication);
        
        return new JwtResponse(jwt);
    }
    
    private Authentication prepareAuthentication(final LoginRequest loginRequest) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        return authentication;
    }
    
}
