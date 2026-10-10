package com.faesa.smartRoute.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RoleDto(
        @JsonProperty("nome")
        String nomePerfil
) {
}
