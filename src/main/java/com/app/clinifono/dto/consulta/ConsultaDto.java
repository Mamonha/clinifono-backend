package com.app.clinifono.dto.consulta;

import com.app.clinifono.dto.paciente.PacienteIntegrationDto;
import com.app.clinifono.dto.usuario.UsuarioIntegrationDto;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record ConsultaDto (
        @NotNull(message = "O dia do agendamento deve ser informado") LocalDate dataAgendamento,
        @NotNull(message = "A hora de inicio deve ser informada") LocalTime horaDeInicio,
        @NotNull(message = "A hora de encerramento deve ser Informada") LocalTime horaDoFim,
        String descricao,
        @NotNull UsuarioIntegrationDto usuario,
        @NotNull PacienteIntegrationDto paciente
        ){
}
