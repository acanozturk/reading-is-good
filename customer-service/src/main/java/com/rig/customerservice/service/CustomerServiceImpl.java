package com.rig.customerservice.service;

import com.rig.customerservice.data.constant.AccountStatus;
import com.rig.customerservice.data.entity.Account;
import com.rig.customerservice.data.entity.Customer;
import com.rig.customerservice.data.entity.CustomerAddress;
import com.rig.customerservice.data.payload.CreateAccountPayload;
import com.rig.customerservice.data.payload.CreateCustomerAddressPayload;
import com.rig.customerservice.data.payload.request.CreateCustomerRequest;
import com.rig.customerservice.data.payload.response.GetOrderResponse;
import com.rig.customerservice.kafka.producer.KafkaProducer;
import com.rig.customerservice.repository.AccountRepository;
import com.rig.customerservice.repository.CustomerAddressRepository;
import com.rig.customerservice.repository.CustomerRepository;
import com.rig.customerservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final AccountRepository accountRepository;

    private final CustomerRepository customerRepository;

    private final CustomerAddressRepository customerAddressRepository;

    private final OrderRepository orderRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;
    
    private final KafkaProducer kafkaProducer;

    @Override
    @Transactional(readOnly = true)
    public List<GetOrderResponse> getOrdersOfCustomer(final int customerId, final Pageable pageable) {
        return orderRepository.findByCustomerId(customerId, pageable)
                .stream()
                .map(order -> modelMapper.map(order, GetOrderResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createCustomer(final CreateCustomerRequest createCustomerRequest) {
        final Account account = saveAccount(createCustomerRequest.getAccount());
        final Customer customer = saveCustomer(createCustomerRequest, account);
        final CustomerAddress customerAddress = saveCustomerAddress(createCustomerRequest.getAddress(), customer);

        log.info("New customer created with id: {}", customer.getId());
        
        kafkaProducer.produce(customer, customerAddress);
    }

    private Account saveAccount(final CreateAccountPayload createAccountPayload) {
        createAccountPayload.setPassword(passwordEncoder.encode(createAccountPayload.getPassword()));

        final Account account = modelMapper.map(createAccountPayload, Account.class);
        account.setStatus(AccountStatus.ACTIVE);

        return accountRepository.saveAndFlush(account);
    }

    private Customer saveCustomer(final CreateCustomerRequest createCustomerRequest, final Account account) {
        final Customer customer = modelMapper.map(createCustomerRequest, Customer.class);
        customer.setAccount(account);

        return customerRepository.saveAndFlush(customer);
    }

    private CustomerAddress saveCustomerAddress(final CreateCustomerAddressPayload createCustomerAddressPayload, final Customer customer) {
        final CustomerAddress customerAddress = modelMapper.map(createCustomerAddressPayload, CustomerAddress.class);
        customerAddress.setCustomer(customer);

        return customerAddressRepository.saveAndFlush(customerAddress);
    }

}
