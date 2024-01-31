package com.rig.apigateway.service.customer.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rig.apigateway.service.customer.client.CustomerServiceClient;
import com.rig.apigateway.service.customer.request.CreateCustomerRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
@Tag(
        name = "Customer Controller",
        description = "Responsible for customer related operations."
)
@SecurityRequirement(name = "API Key")
@SecurityRequirement(name = "Bearer Authentication")
public final class CustomerController {

    private final CustomerServiceClient customerServiceClient;

    @GetMapping("/{customerId}/orders")
    @Operation(summary = "Returns all orders of the customer. Supports pagination")
    public List<ObjectNode> getOrdersOfCustomer(@PathVariable final int customerId,
                                                final Pageable pageable) {
        return customerServiceClient.getOrdersOfCustomer(customerId, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a new customer.")
    public void createCustomer(@Valid @RequestBody final CreateCustomerRequest createCustomerRequest) {
        customerServiceClient.createCustomer(createCustomerRequest);
    }

}
