package com.app.clinifono.dto;

import com.app.clinifono.entities.Consulta;

import java.util.List;

//TODO Aqui tambem verificar a passagem da List<Consulta>
public record ResponseUsuarioDto(Long id, String nome, String email, String telefone, List<Consulta> consultas) {
}
