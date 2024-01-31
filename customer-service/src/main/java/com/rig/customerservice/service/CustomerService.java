package com.rig.customerservice.service;

import com.rig.customerservice.data.payload.request.CreateCustomerRequest;
import com.rig.customerservice.data.payload.response.GetOrderResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    
    List<GetOrderResponse> getOrdersOfCustomer(int customerId, Pageable pageable);
    
    void createCustomer(CreateCustomerRequest createCustomerRequest);
    
}
