package com.faesa.smartRoute.exceptions;

import lombok.Getter;

@Getter
public class RecordNotFoundException extends RuntimeException {

    private boolean isSevere = false;

    public RecordNotFoundException() {
        this("Registro não encontrado com os parâmetros informados");
    }

    public RecordNotFoundException(String message) {
        super(message);
    }

    public RecordNotFoundException(String message, boolean isSevere) {
        super(message);
        this.isSevere = isSevere;
    }

}
