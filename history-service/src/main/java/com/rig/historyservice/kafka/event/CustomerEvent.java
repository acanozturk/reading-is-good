package com.rig.historyservice.kafka.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class CustomerEvent {
    
    private Long customerId;
    private String gender;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private AccountEvent account;
    
}
