package com.app.clinifono.dto.consulta;

import com.app.clinifono.entities.Paciente;
import com.app.clinifono.entities.Usuarios;

import java.time.LocalDateTime;

public record ResponseConsultaDto(Long id, LocalDateTime dataAgendamento, String descricao, boolean isConfirmed, Usuarios usuario, Paciente paciente) {
}
