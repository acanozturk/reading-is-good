package com.rig.customerservice.data.payload.response;

import com.rig.customerservice.data.constant.OrderStatus;
import com.rig.customerservice.data.payload.GetBookPayload;
import com.rig.customerservice.data.payload.GetCustomerAddressPayload;
import com.rig.customerservice.data.payload.GetCustomerPayload;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public final class GetOrderResponse extends AbstractResponse {
    
    private Long id;
    private UUID orderCode;
    private Integer orderQuantity;
    private Double orderPrice;
    private OrderStatus status;
    private GetBookPayload book;
    private GetCustomerPayload customer;
    private GetCustomerAddressPayload deliveryAddress;

}
