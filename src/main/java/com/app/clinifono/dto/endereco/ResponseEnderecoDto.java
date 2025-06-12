package com.app.clinifono.dto.endereco;

import java.time.LocalDateTime;

public record ResponseEnderecoDto(Long id, String nomeRua, String cep, String bairro, String estado, String cidade, String numeroDaCasa,
                                  LocalDateTime createdAt,
                                  LocalDateTime updatedAt,
                                  String createdBy,
                                  String modifiedby
    ) {
}
