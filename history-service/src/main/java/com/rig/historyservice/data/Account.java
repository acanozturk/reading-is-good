package com.rig.historyservice.data;

import lombok.Data;

@Data
public final class Account {

    private Long accountId;

    private String email;

    private String phone;

    private String status;
    
}
