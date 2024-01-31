package com.rig.bookservice.validation.data;

import com.rig.bookservice.data.payload.request.UpdateBookStockRequest;
import com.rig.bookservice.repository.BookRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UpdateBookStockValidator implements ConstraintValidator<UpdateBookStockValid, UpdateBookStockRequest> {

    private final BookRepository bookRepository;

    @Override
    public boolean isValid(UpdateBookStockRequest updateBookStockRequest, ConstraintValidatorContext context) {
        final Long bookId = updateBookStockRequest.getBookId();

        if (!bookRepository.existsById(bookId)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{constraint.book.notFound}").addConstraintViolation();

            return false;
        }

        return isQuantityValid(updateBookStockRequest);
    }

    private boolean isQuantityValid(final UpdateBookStockRequest updateBookStockRequest) {
        return bookRepository.findById(updateBookStockRequest.getBookId())
                .map(bookStock -> updateBookStockRequest.getTotalQuantity() > bookStock.getAvailableQuantity())
                .orElse(false);
    }

}
