package com.app.clinifono.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginDto(
        @NotBlank(message = "Um email deve ser informado") @Email(message = "O email deve ser válido") String email, @NotBlank String senha) {
}
