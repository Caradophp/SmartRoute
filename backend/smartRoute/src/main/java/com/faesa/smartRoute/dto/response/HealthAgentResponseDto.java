package com.faesa.smartRoute.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record HealthAgentResponseDto(
        String id,
        String telefone,
        @JsonProperty("nome")
        String userName,
        String userEmail
) {
}
