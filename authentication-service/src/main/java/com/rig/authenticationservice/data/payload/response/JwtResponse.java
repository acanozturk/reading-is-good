package com.rig.authenticationservice.data.payload.response;

import lombok.Getter;

@Getter
public final class JwtResponse {

    private final String token;
    private final String type;

    public JwtResponse(String token) {
        this.token = token;
        this.type = "Bearer";
    }
    
}
