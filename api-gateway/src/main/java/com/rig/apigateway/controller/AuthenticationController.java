package com.rig.apigateway.controller;

import com.rig.apigateway.client.authentication.AuthenticationServiceClient;
import com.rig.apigateway.client.authentication.request.LoginRequest;
import com.rig.apigateway.client.authentication.response.JwtResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Tag(
        name = "Authentication Controller",
        description = "Responsible for authentication related operations."
)
public final class AuthenticationController {

    private final AuthenticationServiceClient authenticationServiceClient;

    @PostMapping("/login")
    @Operation(summary = "Returns a JWT to be used in further authentications.")
    public JwtResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authenticationServiceClient.login(loginRequest);
    }
    
}
