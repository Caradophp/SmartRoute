package com.faesa.smartRoute.dto;

import com.faesa.smartRoute.annotation.Phone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AssistedPersonDto(
        @Pattern(regexp = "(?:\\s*|[0-9a-fA-F]{8}-(?:[0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12})",
                message = "Identificador da pessoa assistida inválido")
        String id,

        @NotBlank(message = "Nome da pessoa assistida deve ser informado")
        @Size(max = 50, message = "Nome deve ter no máximo 50 caracteres")
        String nome,

        @NotNull(message = "Data de nascimento deve ser informada")
        @PastOrPresent(message = "Data de nascimento não pode estar no futuro")
        LocalDate dataNascimento,

        @NotBlank(message = "Telefone deve ser informado")
        @Phone(message = "Telefone informado é inválido")
        String telefone,

        @NotBlank(message = "Rua deve ser informada")
        @Size(max = 50, message = "Rua deve ter no máximo 50 caracteres")
        String rua,

        @NotNull(message = "Número do endereço deve ser informado")
        @Positive(message = "Número do endereço deve ser positivo")
        Integer numero,

        @NotBlank(message = "Bairro deve ser informado")
        @Size(max = 50, message = "Bairro deve ter no máximo 50 caracteres")
        String bairro,

        @NotBlank(message = "Cidade deve ser informada")
        @Size(max = 50, message = "Cidade deve ter no máximo 50 caracteres")
        String cidade,

        @NotBlank(message = "Território deve ser informado")
        @Pattern(regexp = "[0-9a-fA-F]{8}-(?:[0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}",
                message = "Identificador do território inválido")
        String idTerritorio,

        @NotBlank(message = "Tipo de vulnerabilidade deve ser informado")
        @Pattern(regexp = "[0-9a-fA-F]{8}-(?:[0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}",
                message = "Identificador do tipo de vulnerabilidade inválido")
        String vulnerabilityId
) {
}
