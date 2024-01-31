package com.rig.customerservice.validation.data;

import com.rig.customerservice.repository.AccountRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UniquePhoneValidator implements ConstraintValidator<UniquePhone, String> {

    private final AccountRepository accountRepository;

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        return !accountRepository.existsByPhone(phone);
    }

}
