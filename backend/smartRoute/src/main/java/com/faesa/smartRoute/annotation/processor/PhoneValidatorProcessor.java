package com.faesa.smartRoute.annotation.processor;

import com.faesa.smartRoute.annotation.Phone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneValidatorProcessor implements ConstraintValidator<Phone, Object> {
    @Override
    public void initialize(Phone constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {

        String regexToPhone = "[0-9]{11}";
        String phone = String.valueOf(object).replaceAll("[-()]", "");

        return Pattern.matches(regexToPhone, phone);
    }
}
