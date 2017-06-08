package com.malikov.ticketsystem.util.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Yurii Malikov
 */
@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumber {

    String message() default "Please provide a valid phone number.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
