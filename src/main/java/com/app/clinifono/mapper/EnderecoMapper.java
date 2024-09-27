package com.app.clinifono.mapper;

import com.app.clinifono.dto.endereco.EnderecoDto;
import com.app.clinifono.dto.endereco.EnderecoUpdateDto;
import com.app.clinifono.dto.endereco.ResponseEnderecoDto;
import com.app.clinifono.entities.Endereco;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    Endereco toEntity(EnderecoDto dto);
    ResponseEnderecoDto toDto (Endereco endereco);
    Endereco toUpdateEntity (EnderecoUpdateDto dto);
}
