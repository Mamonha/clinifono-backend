package com.app.clinifono.dto.paciente;

import com.app.clinifono.dto.endereco.ResponseEnderecoDto;
import com.app.clinifono.entities.Consulta;
import com.app.clinifono.entities.Endereco;

import java.time.LocalDateTime;
import java.util.List;

//TODO verificar se esta correto passar List<Endereco> e List<Consulta>
public record ResponsePacienteDto(Long id, String nome, String cpf, LocalDateTime dataNascimento, String telefone, ResponseEnderecoDto endereco) {
}
