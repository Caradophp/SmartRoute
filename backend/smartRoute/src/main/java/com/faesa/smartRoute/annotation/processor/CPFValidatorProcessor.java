package com.faesa.smartRoute.annotation.processor;

import com.faesa.smartRoute.annotation.CPF;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class CPFValidatorProcessor implements ConstraintValidator<CPF, Object> {

    @Override
    public void initialize(CPF constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        String cpf = String.valueOf(object);
        String cleanCpf = cpf.replaceAll("[.-]", "");
        return Pattern.matches("[0-9]{11}", cleanCpf);
    }
}
