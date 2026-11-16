package com.faesa.smartRoute.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record HealthServiceDto(
        @Pattern(regexp = "(?:\\s*|[0-9a-fA-F]{8}-(?:[0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12})",
                message = "Identificador do serviço de saúde inválido")
        String id,

        @NotBlank(message = "Nome do serviço de saúde deve ser informado")
        @Size(max = 50, message = "Nome deve ter no máximo 50 caracteres")
        String nome,

        @NotBlank(message = "Descrição do serviço de saúde deve ser informada")
        @Size(max = 50, message = "Descrição deve ter no máximo 50 caracteres")
        String descricao
) {
}
