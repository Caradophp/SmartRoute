package com.faesa.smartRoute.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AppUserDto(
        String nome,
        String email,
        @JsonProperty("confirmar_email")
        String confirmarEmail,
        String senha,
        @JsonProperty("confirmar_senha")
        String confirmarSenha,
        long cpf,
        String telefone
) {
}
