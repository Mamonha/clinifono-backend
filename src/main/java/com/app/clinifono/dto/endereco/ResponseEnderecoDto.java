package com.app.clinifono.dto.endereco;

public record ResponseEnderecoDto(Long id, String nomeRua, String cep, String bairro, String estado, String cidade, String numeroDaCasa) {
}
