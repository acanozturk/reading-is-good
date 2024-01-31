package com.rig.customerservice.kafka.event;

import com.rig.customerservice.data.entity.Customer;
import com.rig.customerservice.data.entity.CustomerAddress;
import lombok.Getter;

@Getter
public final class CustomerHistoryEvent {

    private Long customerId;
    private String gender;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private AccountEvent account;
    private CustomerAddressEvent address;

    public CustomerHistoryEvent from(final Customer customer, final CustomerAddress customerAddress) {
        this.customerId = customer.getId();
        this.gender = customer.getGender().name();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.dateOfBirth = customer.getDateOfBirth().toString();
        this.account = new AccountEvent().from(customer.getAccount());
        this.address = new CustomerAddressEvent().from(customerAddress);

        return this;
    }

}
