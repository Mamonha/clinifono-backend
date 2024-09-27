package com.app.clinifono.dto.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EnderecoUpdateDto(
        String nomeRua,
        @Pattern(regexp = "^\\d{5}-?\\d{3}$",
                message = "O CEP deve estar no formato 12345-678 ou 12345678")
        String cep,
        String bairro,
        String estado,
        String cidade,
        String numeroDaCasa
) {
}
