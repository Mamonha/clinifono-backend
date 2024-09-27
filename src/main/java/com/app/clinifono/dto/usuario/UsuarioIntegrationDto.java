package com.app.clinifono.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioIntegrationDto(
        @NotNull(message = "O id do usuario deve ser válido")
                                    Long id) {
}
