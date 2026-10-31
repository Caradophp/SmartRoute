package com.faesa.smartRoute.dto;

import com.faesa.smartRoute.annotation.CPF;
import com.faesa.smartRoute.annotation.Unique;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDto(
        @NotBlank(message = "Nome deve ser informado")
        String nome,

        @Unique(field = "email", table = "users", message = "E-mail já cadastrado no sistema")
        @Email(message = "E-mail informado é inválido")
        @NotBlank(message = "E-mail deve ser informado")
        String email,

        @Unique(field = "cpf", table = "users", message = "CPF já cadastrado no sistema")
        @CPF(message = "CPF informado é inválido")
        long cpf,
        RoleDto perfil
) {
}
