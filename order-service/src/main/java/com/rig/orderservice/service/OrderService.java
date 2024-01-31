package com.rig.orderservice.service;

import com.rig.orderservice.data.payload.request.CreateOrderRequest;
import com.rig.orderservice.data.payload.response.GetOrderResponse;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    
    GetOrderResponse getOrder(long orderId);
    
    List<GetOrderResponse> getOrders(LocalDate startDate, LocalDate endDate);
    
    void createOrder(CreateOrderRequest createOrderRequest);
    
}
