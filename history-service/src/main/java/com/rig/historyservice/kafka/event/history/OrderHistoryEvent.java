package com.rig.historyservice.kafka.event.history;

import com.rig.historyservice.kafka.event.BookEvent;
import com.rig.historyservice.kafka.event.CustomerAddressEvent;
import com.rig.historyservice.kafka.event.CustomerEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class OrderHistoryEvent {

    private Long orderId;
    private String orderCode;
    private Integer orderQuantity;
    private Double orderPrice;
    private String status;
    private BookEvent book;
    private CustomerEvent customer;
    private CustomerAddressEvent deliveryAddress;
    

}
