package com.rig.orderservice.data.payload;

import com.rig.orderservice.data.payload.response.AbstractResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class GetCustomerAddressPayload extends AbstractResponse {
    
    private Long id;
    private String title;
    private String country;
    private String city;
    private String district;
    private String street;
    private String houseNumber;
    private String postCode;
    private String description;

}
