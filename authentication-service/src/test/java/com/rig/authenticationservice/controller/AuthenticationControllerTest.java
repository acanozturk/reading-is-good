package com.rig.authenticationservice.controller;

import com.rig.authenticationservice.data.payload.request.LoginRequest;
import com.rig.authenticationservice.data.payload.response.JwtResponse;
import com.rig.authenticationservice.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthenticationControllerTest extends ControllerTest {
    
    @MockBean
    private AuthenticationService authenticationService;

    @Test
    void testLogin() throws Exception {
        final LoginRequest loginRequest = new LoginRequest("test@test.com", "123456");
        final JwtResponse mockJwtResponse = new JwtResponse("mockJwt");

        when(authenticationService.login(any(LoginRequest.class))).thenReturn(mockJwtResponse);
        
        mockMvc.perform(
                post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getAsJson(loginRequest))
                )
                .andExpect(status().isOk())
                .andExpect(content().string(getAsJson(mockJwtResponse)));
        
        verify(authenticationService, times(1)).login(any(LoginRequest.class));
    }

    @Test
    void testLogin_InvalidEmail() throws Exception {
        final LoginRequest loginRequest = new LoginRequest("test.com", "123456");
        
        mockMvc.perform(
                        post("/api/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getAsJson(loginRequest))
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(BindException.class));

        verifyNoInteractions(authenticationService);
    }

    @Test
    void testLogin_InvalidPassword() throws Exception {
        final LoginRequest loginRequest = new LoginRequest("test@test.com", "");

        mockMvc.perform(
                        post("/api/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getAsJson(loginRequest))
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(BindException.class));

        verifyNoInteractions(authenticationService);
    }
    
}