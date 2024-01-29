package com.rig.authenticationservice.data.payload.response;

import lombok.Getter;

import java.time.Instant;

@Getter
public final class ExceptionResponse {

    private final String message;
    private final Instant timestamp;

    public ExceptionResponse(String message) {
        this.message = message;
        this.timestamp = Instant.now();
    }
}
