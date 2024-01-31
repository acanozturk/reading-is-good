package com.rig.historyservice.kafka.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class AccountEvent {

    private Long accountId;
    private String email;
    private String phone;
    private String status;
    
}
