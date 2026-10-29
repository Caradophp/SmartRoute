package com.faesa.smartRoute.annotation;

import com.faesa.smartRoute.annotation.processor.CPFValidatorProcessor;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE, ElementType.RECORD_COMPONENT})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CPFValidatorProcessor.class)
public @interface CPF {

    String message() default "CPF inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
