package com.rig.customerservice.controller;

import com.rig.customerservice.data.payload.request.CreateCustomerRequest;
import com.rig.customerservice.data.payload.response.GetOrderResponse;
import com.rig.customerservice.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
public final class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{customerId}/orders")
    public List<GetOrderResponse> getOrdersOfCustomer(@PathVariable final long customerId, final Pageable pageable) {
        return customerService.getOrdersOfCustomer(customerId, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCustomer(@Valid @RequestBody final CreateCustomerRequest createCustomerRequest) {
        customerService.createCustomer(createCustomerRequest);
    }

}
