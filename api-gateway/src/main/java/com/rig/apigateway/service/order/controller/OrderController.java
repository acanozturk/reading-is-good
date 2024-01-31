package com.rig.apigateway.service.order.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rig.apigateway.service.order.client.OrderServiceClient;
import com.rig.apigateway.service.order.request.CreateOrderRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
@Tag(
        name = "Order Controller",
        description = "Responsible for order related operations."
)
@SecurityRequirement(name = "API Key")
@SecurityRequirement(name = "Bearer Authentication")
public final class OrderController {

    private final OrderServiceClient orderServiceClient;

    @GetMapping
    @Operation(summary = "Returns all orders within a period.")
    public List<ObjectNode> getOrders(@RequestParam final LocalDate startDate,
                                      @RequestParam final LocalDate endDate) {
        return orderServiceClient.getOrders(startDate, endDate);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Returns order of given id.")
    public ObjectNode getOrder(@PathVariable final long orderId) {
        return orderServiceClient.getOrder(orderId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a new order.")
    public void createOrder(@Valid @RequestBody final CreateOrderRequest createOrderRequest) {
        orderServiceClient.createOrder(createOrderRequest);
    }

}
