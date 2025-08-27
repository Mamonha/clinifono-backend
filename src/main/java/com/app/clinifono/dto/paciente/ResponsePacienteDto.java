package com.app.clinifono.dto.paciente;

import com.app.clinifono.dto.endereco.ResponseEnderecoDto;
import com.app.clinifono.entities.Consulta;
import com.app.clinifono.entities.Endereco;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public record ResponsePacienteDto(Long id, String nome, String cpf, LocalDate dataDeNascimento, String telefone, ResponseEnderecoDto endereco,
                                  LocalDateTime createdAt,
                                  LocalDateTime updatedAt,
                                  String createdBy,
                                  String modifiedby) {
}
