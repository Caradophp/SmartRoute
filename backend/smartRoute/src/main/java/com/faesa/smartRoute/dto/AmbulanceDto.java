package com.faesa.smartRoute.dto;

import com.faesa.smartRoute.model.enums.AmbulanceStaus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AmbulanceDto(
        @NotBlank(message = "Placa deve ser informada")
        @Size(max = 10, message = "Placa deve ter no máximo 10 caracteres")
        String placa,

        @NotNull(message = "Status da ambulância deve ser informado")
        AmbulanceStaus status,

        @NotBlank(message = "Motorista deve ser informado")
        @Pattern(regexp = "[0-9a-fA-F]{8}-(?:[0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}",
                message = "Identificador do motorista inválido")
        String driverId,

        @NotBlank(message = "Agente de saúde deve ser informado")
        @Pattern(regexp = "[0-9a-fA-F]{8}-(?:[0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}",
                message = "Identificador do agente de saúde inválido")
        String healthAgentId
) {
}
