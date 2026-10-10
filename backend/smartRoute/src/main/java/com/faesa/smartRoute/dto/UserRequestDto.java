package com.faesa.smartRoute.dto;

public record UserRequestDto(
        String nome,
        String email,
        long cpf,
        RoleDto perfil
) {
}
