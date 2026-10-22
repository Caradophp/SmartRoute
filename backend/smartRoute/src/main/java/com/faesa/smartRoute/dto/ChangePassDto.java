package com.faesa.smartRoute.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChangePassDto(
        String email,
        String senha,
        @JsonProperty("confirmar_senha")
        String confirmarSenha
) {
}
