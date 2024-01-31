package com.rig.orderservice.validation.data;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = CreateOrderValidator.class)
public @interface CreateOrderValid {

    String message() default "{constraint.order.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
