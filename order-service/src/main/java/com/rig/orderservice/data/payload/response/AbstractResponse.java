package com.rig.orderservice.data.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public abstract class AbstractResponse {
    
    private Instant createdAt;
    private String createdBy;
    private Instant updatedAt;
    private String updatedBy;

}
