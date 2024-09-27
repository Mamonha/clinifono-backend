package com.app.clinifono.dto.paciente;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record PacienteUpdateDto(
        String nome,
        @CPF(message = "O CPF deve ser válido")
        String cpf,
        LocalDate dataDeNascimento,
        @Pattern(regexp = "^\\+55 \\d{2} 9\\d{4}-\\d{4}$",
                message = "Número de celular deve estar no formato +55 XX 9XXXX-XXXX")
        String telefone
) {
}
