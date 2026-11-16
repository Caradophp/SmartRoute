package com.faesa.smartRoute.dto;

import com.faesa.smartRoute.annotation.CPF;
import com.faesa.smartRoute.annotation.Phone;
import com.faesa.smartRoute.annotation.Unique;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record AppUserDto(
        @NotBlank(message = "Nome deve ser informado")
        String nome,
        @Email(message = "E-mail inválido")
        @NotBlank(message = "E-mail deve ser informado")
        String email,

        @JsonProperty("confirmar_email")
        @Email(message = "E-mail inválido")
        String confirmarEmail,

        @NotBlank(message = "Senha deve ser informada")
        @Min(message = "Senha deve ter no minímo 8 caractéries", value = 8L)
        String senha,

        @JsonProperty("confirmar_senha")
        String confirmarSenha,

        @Unique(field = "cpf", table = "users", message = "CPF já cadastrado no sistema")
        @CPF
        long cpf,

        @Unique(field = "telefone", table = "app_user", message = "Telefone já cadastrado no sistema")
        @Phone
        String telefone
) {
}
