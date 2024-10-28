package com.app.clinifono.mapper;

import com.app.clinifono.dto.usuario.*;
import com.app.clinifono.entities.Usuarios;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    Usuarios toEntity(UsuarioDto dto);
    ResponseUsuarioDto toDto(Usuarios user);
    Usuarios updateDto (UsuarioUpdateDto dto);
    Usuarios toEntityIntegratio (UsuarioIntegrationDto dto);
    Usuarios toLogin (LoginDto dto);
}
