package com.rig.historyservice.kafka.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class CustomerAddressEvent {

    private Long customerAddressId;
    private String title;
    private String country;
    private String city;
    private String district;
    private String street;
    private String houseNumber;
    private String postCode;
    private String description;
    
}
