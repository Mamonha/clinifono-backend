package com.app.clinifono.dto.consulta;

import com.app.clinifono.entities.Status;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record ConsultaUpdateDto (
        LocalDate dataAgendamento,
        LocalTime horaDeInicio,
        LocalTime horaDoFim,
        String descricao,
        Status status
){
}
