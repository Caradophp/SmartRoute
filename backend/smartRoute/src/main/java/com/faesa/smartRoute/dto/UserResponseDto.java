package com.faesa.smartRoute.dto;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String nome,
        String email,
        long cpf,
        String perfil
) {
}
