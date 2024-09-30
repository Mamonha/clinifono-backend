package com.app.clinifono.dto.usuario;

import com.app.clinifono.entities.Consulta;

import java.util.List;



public record ResponseUsuarioDto(Long id, String nome, String email, String telefone) {
}
