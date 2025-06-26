package com.nnk.springboot.config;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "The password must contain at least 8 characters, one capital letter, one number and one symbol.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

