package com.faesa.smartRoute.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RoleDto(
        @JsonProperty("nome")
        @NotBlank(message = "Nome do perfil deve ser informado")
        @Size(max = 50, message = "Nome do perfil deve ter no máximo 50 caracteres")
        String nomePerfil
) {
}
