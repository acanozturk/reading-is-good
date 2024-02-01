package com.rig.orderservice.controller;

import com.rig.orderservice.data.entity.Book;
import com.rig.orderservice.data.entity.Customer;
import com.rig.orderservice.data.entity.CustomerAddress;
import com.rig.orderservice.data.payload.request.CreateOrderRequest;
import com.rig.orderservice.data.payload.response.GetOrderResponse;
import com.rig.orderservice.exception.NotFoundException;
import com.rig.orderservice.repository.BookRepository;
import com.rig.orderservice.repository.CustomerAddressRepository;
import com.rig.orderservice.repository.CustomerRepository;
import com.rig.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest extends ControllerTest {

    @MockBean
    private OrderService orderService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private CustomerAddressRepository customerAddressRepository;

    @Test
    void testGetOrders() throws Exception {
        final GetOrderResponse getOrderResponse1 = new GetOrderResponse();
        getOrderResponse1.setId(1L);

        final GetOrderResponse getOrderResponse2 = new GetOrderResponse();
        getOrderResponse2.setId(2L);

        final List<GetOrderResponse> orderResponseList = List.of(getOrderResponse1, getOrderResponse2);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.set("startDate", "2024-01-01");
        queryParams.set("endDate", "2024-01-31");

        when(orderService.getOrders(any(LocalDate.class), any(LocalDate.class))).thenReturn(orderResponseList);

        mockMvc.perform(
                        get("/api/v1/orders")
                                .queryParams(queryParams)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(getAsJson(orderResponseList)));

        verify(orderService, times(1)).getOrders(any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    void testGetOrder() throws Exception {
        final GetOrderResponse getOrderResponse = new GetOrderResponse();
        getOrderResponse.setId(1L);

        when(orderService.getOrder(anyLong())).thenReturn(getOrderResponse);

        mockMvc.perform(
                        get("/api/v1/orders/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(getAsJson(getOrderResponse)));

        verify(orderService, times(1)).getOrder(anyLong());
    }

    @Test
    void testGetOrder_OrderNotFound() throws Exception {
        when(orderService.getOrder(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(
                        get("/api/v1/orders/2")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(NotFoundException.class));

        verify(orderService, times(1)).getOrder(anyLong());
    }

    @Test
    void testCreateOrder() throws Exception {
        final Book book = new Book();
        book.setId(1L);
        book.setAvailableQuantity(150);

        final Customer customer = new Customer();
        customer.setId(1L);

        final CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setId(1L);
        customerAddress.setCustomer(customer);

        final CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setBookId(1L);
        createOrderRequest.setCustomerId(1L);
        createOrderRequest.setDeliveryAddressId(1L);
        createOrderRequest.setOrderQuantity(100);

        when(bookRepository.existsById(anyLong())).thenReturn(true);
        when(customerRepository.existsById(anyLong())).thenReturn(true);
        when(customerAddressRepository.existsById(anyLong())).thenReturn(true);
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(customerAddressRepository.findById(anyLong())).thenReturn(Optional.of(customerAddress));

        mockMvc.perform(
                        post("/api/v1/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getAsJson(createOrderRequest))
                )
                .andExpect(status().isCreated());

        verify(orderService, times(1)).createOrder(any(CreateOrderRequest.class));
    }

    @Test
    void testCreateOrder_BookNotFound() throws Exception {
        final CreateOrderRequest createOrderRequest = new CreateOrderRequest();

        when(bookRepository.existsById(anyLong())).thenReturn(true);
        when(customerRepository.existsById(anyLong())).thenReturn(false);

        mockMvc.perform(
                        post("/api/v1/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getAsJson(createOrderRequest))
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(BindException.class));

        verifyNoInteractions(orderService);
    }

    @Test
    void testCreateOrder_CustomerNotFound() throws Exception {
        final CreateOrderRequest createOrderRequest = new CreateOrderRequest();

        when(bookRepository.existsById(anyLong())).thenReturn(false);

        mockMvc.perform(
                        post("/api/v1/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getAsJson(createOrderRequest))
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(BindException.class));

        verifyNoInteractions(orderService);
    }

    @Test
    void testCreateOrder_DeliveryAddressNotFound() throws Exception {
        final CreateOrderRequest createOrderRequest = new CreateOrderRequest();

        when(bookRepository.existsById(anyLong())).thenReturn(true);
        when(customerRepository.existsById(anyLong())).thenReturn(true);
        when(customerAddressRepository.existsById(anyLong())).thenReturn(false);

        mockMvc.perform(
                        post("/api/v1/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getAsJson(createOrderRequest))
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(BindException.class));

        verifyNoInteractions(orderService);
    }

    @Test
    void testCreateOrder_InsufficientStock() throws Exception {
        final Book book = new Book();
        book.setId(1L);
        book.setAvailableQuantity(50);

        final CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setBookId(1L);
        createOrderRequest.setCustomerId(1L);
        createOrderRequest.setDeliveryAddressId(1L);
        createOrderRequest.setOrderQuantity(100);

        when(bookRepository.existsById(anyLong())).thenReturn(true);
        when(customerRepository.existsById(anyLong())).thenReturn(true);
        when(customerAddressRepository.existsById(anyLong())).thenReturn(true);
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        mockMvc.perform(
                        post("/api/v1/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getAsJson(createOrderRequest))
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(BindException.class));

        verifyNoInteractions(orderService);
    }

    @Test
    void testCreateOrder_InvalidDeliveryAddress() throws Exception {
        final Book book = new Book();
        book.setId(1L);
        book.setAvailableQuantity(150);

        final Customer customer = new Customer();
        customer.setId(1L);

        final CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setId(1L);
        customerAddress.setCustomer(customer);

        final CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setBookId(1L);
        createOrderRequest.setCustomerId(2L);
        createOrderRequest.setDeliveryAddressId(1L);
        createOrderRequest.setOrderQuantity(100);

        when(bookRepository.existsById(anyLong())).thenReturn(true);
        when(customerRepository.existsById(anyLong())).thenReturn(true);
        when(customerAddressRepository.existsById(anyLong())).thenReturn(true);
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(customerAddressRepository.findById(anyLong())).thenReturn(Optional.of(customerAddress));

        mockMvc.perform(
                        post("/api/v1/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getAsJson(createOrderRequest))
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(BindException.class));

        verifyNoInteractions(orderService);
    }


}