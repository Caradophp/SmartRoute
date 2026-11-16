package com.faesa.smartRoute.dto.response;

public record AdressResponseDto(
        String id,
        String rua,
        Integer numero,
        String bairro,
        String cidade,
        String nomeTerritorio
) {
}
