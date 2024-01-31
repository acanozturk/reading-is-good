package com.rig.historyservice.data;

import lombok.Data;

@Data
public final class Customer {

    private Long customerId;
    
    private String gender;
    
    private String firstName;
    
    private String lastName;
    
    private String dateOfBirth;
    
    private Account account;
    
}
