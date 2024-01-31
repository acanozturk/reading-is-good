package com.rig.customerservice.kafka.event;

import com.rig.customerservice.data.entity.Account;
import lombok.Getter;

@Getter
public final class AccountEvent {

    private Long accountId;
    private String email;
    private String phone;
    private String status;
    
    public AccountEvent from(final Account account) {
        this.accountId = account.getId();
        this.email = account.getEmail();
        this.phone = account.getPhone();
        this.status = account.getStatus().name();
        
        return this;
    }
    
}
