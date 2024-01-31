package com.rig.orderservice.validation.data;

import com.rig.orderservice.data.payload.request.CreateOrderRequest;
import com.rig.orderservice.repository.BookRepository;
import com.rig.orderservice.repository.CustomerAddressRepository;
import com.rig.orderservice.repository.CustomerRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
@Slf4j
public class CreateOrderValidator implements ConstraintValidator<CreateOrderValid, CreateOrderRequest> {

    private final BookRepository bookRepository;

    private final CustomerRepository customerRepository;

    private final CustomerAddressRepository customerAddressRepository;

    @Override
    public boolean isValid(CreateOrderRequest createOrderRequest, ConstraintValidatorContext context) {
        final Long bookId = createOrderRequest.getBookId();
        final Long customerId = createOrderRequest.getCustomerId();
        final Long deliveryAddressId = createOrderRequest.getDeliveryAddressId();

        if (!bookRepository.existsById(bookId)) {
            return handleError(
                    context,
                    "{constraint.book.notFound}",
                    "Book not found with id: " + bookId
            );
        }

        if (!customerRepository.existsById(customerId)) {
            return handleError(
                    context,
                    "{constraint.customer.notFound}",
                    "Customer not found with id: " + customerId
            );
        }

        if (!customerAddressRepository.existsById(deliveryAddressId)) {
            return handleError(
                    context,
                    "{constraint.customerAddress.notFound}",
                    "Customer address not found with id: " + deliveryAddressId
            );
        }

        if (!isStockAvailable(createOrderRequest)) {
            return handleError(
                    context,
                    "{constraint.bookStock.insufficient}",
                    "Book stock is insufficient for given order quantity."
            );
        }

        if (!deliveryAddressBelongsToCustomer(createOrderRequest)) {
            return handleError(
                    context,
                    "{constraint.deliveryAddress.invalid}",
                    "Delivery address does not belong to the customer."
            );
        }

        return true;
    }

    private boolean isStockAvailable(final CreateOrderRequest createOrderRequest) {
        return bookRepository.findById(createOrderRequest.getBookId())
                .map(book -> book.getAvailableQuantity() >= createOrderRequest.getOrderQuantity())
                .orElse(false);
    }

    private boolean deliveryAddressBelongsToCustomer(final CreateOrderRequest createOrderRequest) {
        return customerAddressRepository.findById(createOrderRequest.getDeliveryAddressId())
                .map(customerAddress -> Objects.equals(customerAddress.getCustomer().getId(), createOrderRequest.getCustomerId()))
                .orElse(false);
    }

    private boolean handleError(final ConstraintValidatorContext context,
                                final String templateMessage,
                                final String reason) {
        log.error("Cannot create order. Reason: {}", reason);

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(templateMessage).addConstraintViolation();

        return false;
    }

}
