package com.faesa.smartRoute.dto;

import java.time.LocalDate;

public record AssistedPersonDto(
        String id,
        String nome,
        LocalDate dataNascimento,
        String telefone,
        String rua,
        Integer numero,
        String bairro,
        String cidade,
        String idTerritorio,
        String vulnerabilityId
) {
}
