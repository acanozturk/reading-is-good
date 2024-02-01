package com.rig.customerservice.service;

import com.rig.customerservice.MockedTest;
import com.rig.customerservice.data.constant.Gender;
import com.rig.customerservice.data.entity.Account;
import com.rig.customerservice.data.entity.Customer;
import com.rig.customerservice.data.entity.CustomerAddress;
import com.rig.customerservice.data.entity.Order;
import com.rig.customerservice.data.payload.CreateAccountPayload;
import com.rig.customerservice.data.payload.CreateCustomerAddressPayload;
import com.rig.customerservice.data.payload.request.CreateCustomerRequest;
import com.rig.customerservice.data.payload.response.GetOrderResponse;
import com.rig.customerservice.kafka.producer.KafkaProducer;
import com.rig.customerservice.repository.AccountRepository;
import com.rig.customerservice.repository.CustomerAddressRepository;
import com.rig.customerservice.repository.CustomerRepository;
import com.rig.customerservice.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;

class CustomerServiceImplTest extends MockedTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerAddressRepository customerAddressRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void testGetOrdersOfCustomer() {
        final long customerId = 1L;
        final Pageable pageable = PageRequest.of(0, 10);
        final List<Order> orders = List.of(new Order());
        
        when(orderRepository.findByCustomerId(customerId, pageable)).thenReturn(orders);

        customerService.getOrdersOfCustomer(customerId, pageable);

        verify(orderRepository, times(1)).findByCustomerId(customerId, pageable);
        verify(modelMapper, times(1)).map(any(), eq(GetOrderResponse.class));
    }

    @Test
    void testCreateCustomer() {
        final CreateCustomerRequest createCustomerRequest = buildCustomerRequest();
        final Account account = new Account();
        account.setId(1L);
        
        final CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setId(1L);
        
        final Customer customer = new Customer();
        customer.setId(1L);
        customer.setAccount(account);
        customer.setAddresses(List.of(customerAddress));

        when(modelMapper.map(createCustomerRequest.getAccount(), Account.class)).thenReturn(account);
        when(modelMapper.map(createCustomerRequest, Customer.class)).thenReturn(customer);
        when(modelMapper.map(createCustomerRequest.getAddress(), CustomerAddress.class)).thenReturn(customerAddress);
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(customer);
        when(customerAddressRepository.saveAndFlush(any(CustomerAddress.class))).thenReturn(customerAddress);

        customerService.createCustomer(createCustomerRequest);

        verify(accountRepository, times(1)).saveAndFlush(account);
        verify(customerRepository, times(1)).saveAndFlush(customer);
        verify(customerAddressRepository, times(1)).saveAndFlush(customerAddress);
        verify(kafkaProducer, times(1)).produce(customer, customerAddress);
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