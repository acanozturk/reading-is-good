package com.rig.customerservice.validation.data;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = UniquePhoneValidator.class)
public @interface UniquePhone {

    String message() default "{constraint.phone.unique}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
