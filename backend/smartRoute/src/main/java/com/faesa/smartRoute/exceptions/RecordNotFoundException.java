package com.faesa.smartRoute.exceptions;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException() {
        this("Registro não encontrado com os parâmetros informados");
    }

    public RecordNotFoundException(String message) {
        super(message);
    }
}
