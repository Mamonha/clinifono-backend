package com.app.clinifono.mapper;

import com.app.clinifono.dto.paciente.PacienteDto;
import com.app.clinifono.dto.paciente.PacienteUpdateDto;
import com.app.clinifono.dto.paciente.ResponsePacienteDto;
import com.app.clinifono.entities.Paciente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EnderecoMapper.class})
public interface PacienteMapper {

    @Mapping(source = "endereco", target = "enderecos")
    @Mapping(source = "dataDeNascimento", target = "dataNascimento")
    Paciente toEntity (PacienteDto dto);
    @Mapping(source = "enderecos", target = "endereco")
    @Mapping(source = "dataNascimento", target = "dataDeNascimento")
    ResponsePacienteDto toDto (Paciente paciente);
    @Mapping(source = "dataDeNascimento", target = "dataNascimento")
    Paciente toUpdateEntity (PacienteUpdateDto dto);
}
