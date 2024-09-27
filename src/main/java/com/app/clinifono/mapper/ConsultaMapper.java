package com.app.clinifono.mapper;

import com.app.clinifono.dto.consulta.ConsultaConfirmDto;
import com.app.clinifono.dto.consulta.ConsultaDto;
import com.app.clinifono.dto.consulta.ConsultaUpdateDto;
import com.app.clinifono.dto.consulta.ResponseConsultaDto;
import com.app.clinifono.entities.Consulta;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PacienteMapper.class, UserMapper.class})
public interface ConsultaMapper {

    Consulta toEntity(ConsultaDto dto);
    ResponseConsultaDto toDto (Consulta consulta);
    Consulta toUpdateEntity (ConsultaUpdateDto dto);
    Consulta toConfirm (ConsultaConfirmDto dto);
}
