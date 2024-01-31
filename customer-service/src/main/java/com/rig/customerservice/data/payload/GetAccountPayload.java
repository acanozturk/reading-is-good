package com.rig.customerservice.data.payload;

import com.rig.customerservice.data.constant.AccountStatus;
import com.rig.customerservice.data.payload.response.AbstractResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class GetAccountPayload extends AbstractResponse {
    
    private Long id;
    private String email;
    private String phone;
    private AccountStatus status;

}
