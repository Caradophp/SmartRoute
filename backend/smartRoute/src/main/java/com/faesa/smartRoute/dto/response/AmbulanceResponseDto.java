
package com.faesa.smartRoute.dto.response;

public record AmbulanceResponseDto(
        String id,
        String placa,
        String status,
        String driverId,
        String healthAgentId
) {
}
