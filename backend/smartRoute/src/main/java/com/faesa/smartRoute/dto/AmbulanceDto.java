package com.faesa.smartRoute.dto;

import com.faesa.smartRoute.model.enums.AmbulanceStaus;

public record AmbulanceDto(
        String placa,
        AmbulanceStaus status,
        String driverId,
        String healthAgentId
) {
}
