package com.rig.apigateway.client.authentication.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class JwtResponse {

    private String token;
    private String type;
    
}
