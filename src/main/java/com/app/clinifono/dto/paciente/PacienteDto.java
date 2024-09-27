package com.app.clinifono.dto.paciente;

import com.app.clinifono.dto.endereco.EnderecoDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record PacienteDto (
        @NotBlank(message = "O nome do paciente não pode ser nulo") String nome,
        @CPF @NotBlank (message = "O cpf deve ser válido") String cpf,
        @NotNull(message = "A data deve ser em um formato válido") LocalDate dataDeNascimento,
        @Pattern(regexp = "^\\+55 \\d{2} 9\\d{4}-\\d{4}$",
                message = "Número de celular deve estar no formato +55 XX 9XXXX-XXXX")
        @NotBlank String telefone,
        @NotNull EnderecoDto endereco
        ){
}
