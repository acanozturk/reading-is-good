package com.rig.customerservice.data.payload;

import com.rig.customerservice.data.constant.Gender;
import com.rig.customerservice.data.payload.response.AbstractResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public final class GetCustomerPayload extends AbstractResponse {
    
    private Long id;
    private Gender gender;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private GetAccountPayload account;

}
