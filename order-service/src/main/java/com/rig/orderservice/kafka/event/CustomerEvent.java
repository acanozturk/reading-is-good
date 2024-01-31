package com.rig.orderservice.kafka.event;

import com.rig.orderservice.data.entity.Customer;
import lombok.Getter;

@Getter
public final class CustomerEvent {
    
    private Long customerId;
    private String gender;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private AccountEvent account;
    
    public CustomerEvent from(final Customer customer) {
        this.customerId = customer.getId();
        this.gender = customer.getGender().name();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.dateOfBirth = customer.getDateOfBirth().toString();
        this.account = new AccountEvent().from(customer.getAccount());
        
        return this;
    }
    
}
