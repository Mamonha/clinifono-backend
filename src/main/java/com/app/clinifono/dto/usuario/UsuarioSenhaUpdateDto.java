package com.app.clinifono.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioSenhaUpdateDto(
        @NotBlank String senhaAtual,
        @NotBlank String novaSenha
) {
}
