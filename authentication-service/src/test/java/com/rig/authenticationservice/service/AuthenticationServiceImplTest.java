package com.rig.authenticationservice.service;

import com.rig.authenticationservice.MockedTest;
import com.rig.authenticationservice.data.payload.request.LoginRequest;
import com.rig.authenticationservice.data.payload.response.JwtResponse;
import com.rig.authenticationservice.security.jwt.JwtManager;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuthenticationServiceImplTest extends MockedTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtManager jwtManager;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    void testLogin() {
        final LoginRequest loginRequest = new LoginRequest("test@test.com", "123456");
        final Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        final String jwt = "mockJwt";
        
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
        when(jwtManager.generateJwt(authentication)).thenReturn(jwt);
        
        final JwtResponse jwtResponse = authenticationService.login(loginRequest);

        assertThat(jwtResponse.getType()).isEqualTo("Bearer");
        assertThat(jwtResponse.getToken()).isEqualTo(jwt);
    }

}