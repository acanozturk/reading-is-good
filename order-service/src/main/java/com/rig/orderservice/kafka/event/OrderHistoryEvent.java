package com.rig.orderservice.kafka.event;

import com.rig.orderservice.data.entity.Order;
import lombok.Getter;

@Getter
public final class OrderHistoryEvent {

    private Long orderId;
    private String orderCode;
    private Integer orderQuantity;
    private Double orderPrice;
    private String status;
    private BookEvent book;
    private CustomerEvent customer;
    private CustomerAddressEvent deliveryAddress;

    public OrderHistoryEvent from(final Order order) {
        this.orderId = order.getId();
        this.orderCode = order.getOrderCode().toString();
        this.orderQuantity = order.getOrderQuantity();
        this.orderPrice = order.getOrderPrice();
        this.status = order.getStatus().name();
        this.book = new BookEvent().from(order.getBook());
        this.customer = new CustomerEvent().from(order.getCustomer());
        this.deliveryAddress = new CustomerAddressEvent().from(order.getDeliveryAddress());

        return this;
    }

}
