package com.app.clinifono.dto.usuario;

import java.time.LocalDateTime;

public record AuditReportDto(
    String tabela,
    Long id,
    String nome,
    String email,
    String telefone,
    LocalDateTime dataCriacao,
    LocalDateTime dataModificacao,
    String criadoPor,
    String modificadoPor
) {
} 