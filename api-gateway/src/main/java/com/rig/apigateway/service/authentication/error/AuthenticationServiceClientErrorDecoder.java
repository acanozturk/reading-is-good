package com.rig.apigateway.service.authentication.error;

import com.rig.apigateway.exception.ClientBadRequestException;
import com.rig.apigateway.exception.ClientInternalServerErrorException;
import com.rig.apigateway.exception.LoginException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class AuthenticationServiceClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()) {
            case 400 -> new ClientBadRequestException();
            case 401 -> new LoginException();
            default -> new ClientInternalServerErrorException();
        };
    }
}
