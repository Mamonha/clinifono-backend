package com.app.clinifono.mapper;

import com.app.clinifono.dto.paciente.PacienteDto;
import com.app.clinifono.dto.paciente.PacienteUpdateDto;
import com.app.clinifono.dto.paciente.ResponsePacienteDto;
import com.app.clinifono.entities.Paciente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {EnderecoMapper.class})
public interface PacienteMapper {

    Paciente toEntity (PacienteDto dto);
    ResponsePacienteDto toDto (Paciente paciente);
    Paciente toUpdateEntity (PacienteUpdateDto dto);
}
