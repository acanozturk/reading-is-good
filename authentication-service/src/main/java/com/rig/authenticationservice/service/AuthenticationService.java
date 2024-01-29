package com.rig.authenticationservice.service;

import com.rig.authenticationservice.data.payload.request.LoginRequest;
import com.rig.authenticationservice.data.payload.response.JwtResponse;

public interface AuthenticationService {
    
    JwtResponse login(LoginRequest loginRequest);
    
}
