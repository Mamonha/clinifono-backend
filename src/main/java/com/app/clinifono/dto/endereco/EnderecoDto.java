package com.app.clinifono.dto.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.NoArgsConstructor;

public record EnderecoDto (
        @NotBlank(message = "O nome da rua não pode estar em branco") String nomeRua,
        @Pattern(regexp = "^\\d{5}-?\\d{3}$",
                message = "O CEP deve estar no formato 12345-678 ou 12345678")
        @NotBlank String cep,
        @NotBlank(message = "O Bairro não pode estar em branco") String bairro,
        @NotBlank(message = "O Estado não pode estar em branco") String estado,
        @NotBlank(message = "A cidade não pode estar em branco") String cidade,
        @NotBlank(message = "Numero da casa") String numeroDaCasa
){
}
