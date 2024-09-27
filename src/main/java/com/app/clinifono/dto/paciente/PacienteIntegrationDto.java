package com.app.clinifono.dto.paciente;

import jakarta.validation.constraints.NotNull;

public record PacienteIntegrationDto(
        @NotNull Long id) {
}
