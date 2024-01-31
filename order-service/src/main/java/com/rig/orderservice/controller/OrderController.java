package com.rig.orderservice.controller;

import com.rig.orderservice.data.payload.request.CreateOrderRequest;
import com.rig.orderservice.data.payload.response.GetOrderResponse;
import com.rig.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public final class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<GetOrderResponse> getOrders(@RequestParam final LocalDate startDate,
                                            @RequestParam final LocalDate endDate) {
        return orderService.getOrders(startDate, endDate);
    }

    @GetMapping("/{orderId}")
    public GetOrderResponse getOrder(@PathVariable final long orderId) {
        return orderService.getOrder(orderId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@Valid @RequestBody final CreateOrderRequest createOrderRequest) {
        orderService.createOrder(createOrderRequest);
    }

}
