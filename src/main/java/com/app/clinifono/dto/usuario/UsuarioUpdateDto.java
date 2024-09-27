package com.app.clinifono.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UsuarioUpdateDto (
        @NotNull(message = "O campo nome não pode ser nulo") String nome,
        @Email(message = "O email deve ser válido") String email,
        @Pattern(regexp = "^\\+55 \\d{2} 9\\d{4}-\\d{4}$",
                message = "Número de celular deve estar no formato +55 XX 9XXXX-XXXX")
        String telefone
) {
}
