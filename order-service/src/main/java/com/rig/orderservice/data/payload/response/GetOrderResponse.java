package com.rig.orderservice.data.payload.response;

import com.rig.orderservice.data.constant.OrderStatus;
import com.rig.orderservice.data.payload.GetBookPayload;
import com.rig.orderservice.data.payload.GetCustomerAddressPayload;
import com.rig.orderservice.data.payload.GetCustomerPayload;
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
