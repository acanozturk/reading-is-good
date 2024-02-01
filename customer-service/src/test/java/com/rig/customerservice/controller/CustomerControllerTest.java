package com.rig.customerservice.controller;

import com.rig.customerservice.data.constant.Gender;
import com.rig.customerservice.data.payload.CreateAccountPayload;
import com.rig.customerservice.data.payload.CreateCustomerAddressPayload;
import com.rig.customerservice.data.payload.request.CreateCustomerRequest;
import com.rig.customerservice.data.payload.response.GetOrderResponse;
import com.rig.customerservice.repository.AccountRepository;
import com.rig.customerservice.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindException;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest extends ControllerTest {

    @MockBean
    private CustomerService customerService;

    @MockBean
    private AccountRepository accountRepository;

    @Test
    void testGetOrdersOfCustomer() throws Exception {
        final GetOrderResponse getOrderResponse1 = new GetOrderResponse();
        getOrderResponse1.setId(1L);

        final GetOrderResponse getOrderResponse2 = new GetOrderResponse();
        getOrderResponse2.setId(2L);

        final MultiValueMap<String, String> page0 = new LinkedMultiValueMap<>();
        page0.set("page", "0");
        page0.set("size", "1");

        final MultiValueMap<String, String> page1 = new LinkedMultiValueMap<>();
        page1.set("page", "1");
        page1.set("size", "1");

        when(customerService.getOrdersOfCustomer(eq(1L), any(Pageable.class))).thenReturn(List.of(getOrderResponse1));
        when(customerService.getOrdersOfCustomer(eq(2L), any(Pageable.class))).thenReturn(List.of(getOrderResponse2));

        mockMvc.perform(
                        get("/api/v1/customers/1/orders")
                                .queryParams(page0)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(getAsJson(List.of(getOrderResponse1))));

        mockMvc.perform(
                        get("/api/v1/customers/2/orders")
                                .queryParams(page1)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(getAsJson(List.of(getOrderResponse2))));


        verify(customerService, times(2)).getOrdersOfCustomer(any(Long.class), any(Pageable.class));
    }

    @Test
    void testCreateCustomer() throws Exception {
        final CreateCustomerRequest createCustomerRequest = buildCustomerRequest();

        when(accountRepository.existsByEmail(anyString())).thenReturn(false);
        when(accountRepository.existsByPhone(anyString())).thenReturn(false);

        mockMvc.perform(
                 post("/api/v1/customers")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(getAsJson(createCustomerRequest))
                )
                .andExpect(status().isCreated());

        verify(customerService, times(1)).createCustomer(any(CreateCustomerRequest.class));
    }

    @Test
    void testCreateCustomer_EmailExists() throws Exception {
        final CreateCustomerRequest createCustomerRequest = buildCustomerRequest();

        when(accountRepository.existsByEmail(anyString())).thenReturn(true);
        when(accountRepository.existsByPhone(anyString())).thenReturn(false);

        mockMvc.perform(
                        post("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getAsJson(createCustomerRequest))
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(BindException.class));

        verifyNoInteractions(customerService);
    }

    @Test
    void testCreateCustomer_PhoneExists() throws Exception {
        final CreateCustomerRequest createCustomerRequest = buildCustomerRequest();

        when(accountRepository.existsByEmail(anyString())).thenReturn(false);
        when(accountRepository.existsByPhone(anyString())).thenReturn(true);

        mockMvc.perform(
                        post("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getAsJson(createCustomerRequest))
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(BindException.class));

        verifyNoInteractions(customerService);
    }
    
    private CreateCustomerRequest buildCustomerRequest() {
        final CreateAccountPayload createAccountPayload = new CreateAccountPayload();
        createAccountPayload.setEmail("test@test.com");
        createAccountPayload.setPassword("123456");
        createAccountPayload.setPhone("01234567890");

        final CreateCustomerAddressPayload createCustomerAddressPayload = new CreateCustomerAddressPayload();
        createCustomerAddressPayload.setTitle("Title");
        createCustomerAddressPayload.setCountry("Country");
        createCustomerAddressPayload.setCity("City");
        createCustomerAddressPayload.setDistrict("District");
        createCustomerAddressPayload.setStreet("Street");
        createCustomerAddressPayload.setHouseNumber("1");
        createCustomerAddressPayload.setPostCode("123");
        createCustomerAddressPayload.setDescription("Description");

        final CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();
        createCustomerRequest.setGender(Gender.FEMALE);
        createCustomerRequest.setFirstName("Firstname");
        createCustomerRequest.setLastName("Lastname");
        createCustomerRequest.setDateOfBirth(LocalDate.now().minusYears(20));
        createCustomerRequest.setAccount(createAccountPayload);
        createCustomerRequest.setAddress(createCustomerAddressPayload);
        
        return createCustomerRequest;
    }

}