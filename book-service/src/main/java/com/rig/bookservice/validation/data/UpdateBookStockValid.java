package com.rig.bookservice.validation.data;

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
@Constraint(validatedBy = UpdateBookStockValidator.class)
public @interface UpdateBookStockValid {

    String message() default "{constraint.totalQuantity.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
