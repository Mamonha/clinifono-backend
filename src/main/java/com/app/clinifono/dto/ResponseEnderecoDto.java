package com.app.clinifono.dto;

public record ResponseEnderecoDto(Long id, String nomeRua, String cep, String bairro, String estado, String cidade) {
}
