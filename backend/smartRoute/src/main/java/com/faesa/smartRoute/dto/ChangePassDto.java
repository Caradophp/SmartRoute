package com.faesa.smartRoute.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ChangePassDto(
        @NotBlank(message = "E-mail deve ser informado")
        @Email(message = "E-mail informado é inválido")
        String email,

        @NotBlank(message = "Senha deve ser informada")
        @Min(message = "Senha deve ter no minímo 8 caratéries", value = 8L)
        String senha,

        @JsonProperty("confirmar_senha")
        String confirmarSenha
) {
}
