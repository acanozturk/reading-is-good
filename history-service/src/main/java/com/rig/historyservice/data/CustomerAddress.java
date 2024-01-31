package com.rig.historyservice.data;

import lombok.Data;

@Data
public final class CustomerAddress {

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
