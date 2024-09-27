package com.app.clinifono.mapper;

import com.app.clinifono.dto.usuario.ResponseUsuarioDto;
import com.app.clinifono.dto.usuario.UsuarioDto;
import com.app.clinifono.dto.usuario.UsuarioIntegrationDto;
import com.app.clinifono.dto.usuario.UsuarioUpdateDto;
import com.app.clinifono.entities.Usuarios;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    Usuarios toEntity(UsuarioDto dto);
    ResponseUsuarioDto toDto(Usuarios user);
    Usuarios updateDto (UsuarioUpdateDto dto);
    Usuarios toEntityIntegratio (UsuarioIntegrationDto dto);
}
