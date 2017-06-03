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
// TODO: 5/5/2017 what is retention?
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumber {

    String message() default "Please provide a valid phone number.";
    // TODO: 5/5/2017 change it dto String message() default "{Phone}" #Custom validation annotation Phone=Invalid format, valid formats are 1234567890, 123-456-7890 x1234;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
