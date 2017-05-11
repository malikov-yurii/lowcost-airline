package com.malikov.ticketsystem.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
/**
 * @author Yurii Malikov
 */
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    @Override
    public void initialize(PhoneNumber paramA) {}

    @Override
    public boolean isValid(String phoneNo, ConstraintValidatorContext ctx) {
        //Validate phoneNumber numbers of format "+1234567890"
        if (phoneNo.matches("\\+\\d{11,14}")) return true;

        else return false;
    }

}
