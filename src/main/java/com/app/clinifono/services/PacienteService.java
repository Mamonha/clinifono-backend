package com.app.clinifono.services;

import com.app.clinifono.configuration.exceptions.EntityNotFoundException;
import com.app.clinifono.configuration.exceptions.UniqueValueException;
import com.app.clinifono.entities.Paciente;
import com.app.clinifono.repositories.EnderecoRepository;
import com.app.clinifono.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Transactional
    public Paciente save(Paciente paciente){
        try {
            if (paciente.getEnderecos() != null) {
                paciente.getEnderecos().setPaciente(paciente);
            }
            return pacienteRepository.save(paciente);
        } catch (DataIntegrityViolationException ex){
            throw new UniqueValueException("Cpf já cadastrado");
        }

    }

    public List<Integer> contarPacientesPorMes() {
        List<Integer> totaisPorMes = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            LocalDate inicio = LocalDate.of(LocalDate.now().getYear(), Month.of(month), 1);
            LocalDate fim = inicio.plusMonths(1).minusDays(1);
            int total = pacienteRepository.countByDataCadastroBetween(inicio, fim);
            totaisPorMes.add(total);
            System.out.println("total paciente mes:" + totaisPorMes);
        }
        return totaisPorMes;
    }

    @Transactional
    public Paciente update(Paciente paciente, Long id){
        try {
            var pacient = findById(id);

            if (paciente.getNome() != null && !paciente.getNome().isBlank()) {
                pacient.setNome(paciente.getNome());
            }

            if (paciente.getCpf() != null && !paciente.getCpf().isBlank()) {
                pacient.setCpf(paciente.getCpf());
            }

            if (paciente.getTelefone() != null && !paciente.getTelefone().isBlank()) {
                pacient.setTelefone(paciente.getTelefone());
            }

            if (paciente.getDataNascimento() != null) {
                pacient.setDataNascimento(paciente.getDataNascimento());
            }

            pacient.setId(id);
            return pacienteRepository.save(pacient);
        } catch (DataIntegrityViolationException ex){
            throw new UniqueValueException("Cpf já cadastrado!");
        }

    }

    @Transactional
    public void delete(Long id){
        pacienteRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Paciente findById(Long id){
        return pacienteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Paciente> findAll(){
        return pacienteRepository.findAll();
    }
}
