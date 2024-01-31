package com.rig.historyservice.kafka.event.history;

import com.rig.historyservice.kafka.event.AccountEvent;
import com.rig.historyservice.kafka.event.CustomerAddressEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class CustomerHistoryEvent {

    private Long customerId;
    private String gender;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private AccountEvent account;
    private CustomerAddressEvent address;
    
}
