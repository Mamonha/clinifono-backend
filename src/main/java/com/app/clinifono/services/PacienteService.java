package com.app.clinifono.services;

import com.app.clinifono.entities.Paciente;
import com.app.clinifono.repositories.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Transactional
    public Paciente save(Paciente paciente){return pacienteRepository.save(paciente);
    }

    @Transactional
    public Paciente update(Paciente paciente, Long id){
        paciente.setId(id);
        return pacienteRepository.save(paciente);
    }

    @Transactional
    public void delete(Long id){
        pacienteRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Paciente findById(Long id){
        return pacienteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Paciente> findAll(){
        return pacienteRepository.findAll();
    }
}
