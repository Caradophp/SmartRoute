package com.faesa.smartRoute.annotation.processor;

import com.faesa.smartRoute.annotation.Unique;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.regex.Pattern;

public class UniqueValidatorProcessor implements ConstraintValidator<Unique, Object> {

    private final JdbcTemplate jdbcTemplate;

    private String field;
    private String table;

    public UniqueValidatorProcessor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.table = constraintAnnotation.table();

        if (!Pattern.matches("[a-zA-Z_][a-zA-Z0-9_]*", field)
                || !Pattern.matches("[a-zA-Z_][a-zA-Z0-9_]*", table)) {
            throw new IllegalArgumentException(
                    "Nome de tabela ou coluna inválido na anotação @Unique"
            );
        }
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        String sql = "SELECT COUNT(*) FROM " + table + " WHERE " + field + " = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, value);

        return count != null && count == 0;
    }
}
