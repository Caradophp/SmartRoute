package com.faesa.smartRoute.dto;

import com.faesa.smartRoute.annotation.Phone;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record EmergencyDispachDto(
        @NotNull(message = "Latitude deve ser informada")
        @DecimalMin(value = "-90.0", message = "Latitude deve ser no mínimo -90")
        @DecimalMax(value = "90.0", message = "Latitude deve ser no máximo 90")
        Double latitude,

        @NotNull(message = "Longitude deve ser informada")
        @DecimalMin(value = "-180.0", message = "Longitude deve ser no mínimo -180")
        @DecimalMax(value = "180.0", message = "Longitude deve ser no máximo 180")
        Double longitude,

        @NotBlank(message = "Identificador do usuário deve ser informado")
        @Pattern(regexp = "[0-9a-fA-F]{8}-(?:[0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}",
                message = "Identificador do usuário inválido")
        String userId,

        @NotBlank(message = "Nome do usuário deve ser informado")
        String username,

        @NotBlank(message = "E-mail do usuário deve ser informado")
        @Email(message = "E-mail do usuário inválido")
        String useremail,

        @NotBlank(message = "Telefone do usuário deve ser informado")
        @Phone(message = "Telefone do usuário inválido")
        String userphonenumber
) {
}
