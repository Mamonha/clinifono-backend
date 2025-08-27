package com.app.clinifono.dto.consulta;

import com.app.clinifono.dto.paciente.ResponsePacienteDto;
import com.app.clinifono.dto.usuario.ResponseUsuarioDto;
import com.app.clinifono.entities.Paciente;
import com.app.clinifono.entities.Status;
import com.app.clinifono.entities.Usuarios;
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record ResponseConsultaDto(
        Long id,
        LocalDateTime dataAgendamento,
        LocalTime horaDeInicio,
        LocalTime horaDoFim,
        String descricao,
        Status status,
        ResponseUsuarioDto usuario,
        ResponsePacienteDto paciente,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String modifiedby
        ) {
}
