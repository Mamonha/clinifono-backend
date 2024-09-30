package com.app.clinifono.dto.consulta;

import com.app.clinifono.dto.paciente.ConsultasPorPacienteDto;

import java.util.List;

public record ConsultaDashboardDto(
        long totalConsultas,
        long consultasConfirmadas,
        long consultasCanceladas,
        List<ConsultasPorPacienteDto> consultasPorPaciente,
        List<ConsultasPorMesDto> consultasPorMes
) {
}