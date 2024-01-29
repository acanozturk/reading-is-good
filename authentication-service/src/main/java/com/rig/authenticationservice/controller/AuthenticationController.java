package com.rig.authenticationservice.controller;

import com.rig.authenticationservice.data.payload.request.LoginRequest;
import com.rig.authenticationservice.data.payload.response.JwtResponse;
import com.rig.authenticationservice.service.AuthenticationService;
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

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(summary = "Returns a JWT to be used in further authentications.")
    public JwtResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authenticationService.login(loginRequest);
    }
    
}
