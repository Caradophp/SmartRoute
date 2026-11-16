package com.faesa.smartRoute.dto.request;

public record AdressRequestDto(
        String id,
        String rua,
        Integer numero,
        String bairro,
        String cidade,
        String territoryId
) {
}
