package com.rig.customerservice.kafka.event;

import com.rig.customerservice.data.entity.CustomerAddress;
import lombok.Getter;

@Getter
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

    public CustomerAddressEvent from(final CustomerAddress customerAddress) {
        this.customerAddressId = customerAddress.getId();
        this.title = customerAddress.getTitle();
        this.country = customerAddress.getCountry();
        this.city = customerAddress.getCity();
        this.district = customerAddress.getDistrict();
        this.street = customerAddress.getStreet();
        this.houseNumber = customerAddress.getHouseNumber();
        this.postCode = customerAddress.getPostCode();
        this.description = customerAddress.getDescription();
        
        return this;
    }
}
