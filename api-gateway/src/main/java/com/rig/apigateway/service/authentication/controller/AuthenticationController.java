package com.rig.apigateway.service.authentication.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rig.apigateway.service.authentication.client.AuthenticationServiceClient;
import com.rig.apigateway.service.authentication.request.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "API Key")
public final class AuthenticationController {

    private final AuthenticationServiceClient authenticationServiceClient;

    @PostMapping("/login")
    @Operation(summary = "Returns a JWT to be used in further authentications.")
    public ObjectNode login(@Valid @RequestBody final LoginRequest loginRequest) {
        return authenticationServiceClient.login(loginRequest);
    }
    
}
