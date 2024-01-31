package com.rig.orderservice.data.payload;

import com.rig.orderservice.data.constant.AccountStatus;
import com.rig.orderservice.data.payload.response.AbstractResponse;
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
