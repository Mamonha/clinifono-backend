package com.app.clinifono.services;

import com.app.clinifono.entities.Consulta;
import com.app.clinifono.entities.Paciente;
import com.app.clinifono.repositories.ConsultaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Transactional
    public Consulta save(Consulta consulta){return consultaRepository.save(consulta);
    }

    @Transactional
    public Consulta update(Consulta consulta, Long id){
        consulta.setId(id);
        return consultaRepository.save(consulta);
    }

    @Transactional
    public void delete(Long id){consultaRepository.deleteById(id);}

    @Transactional(readOnly = true)
    public Consulta findById(Long id){
        return consultaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("consulta não encontrada"));
    }

    @Transactional(readOnly = true)
    public List<Consulta> findAll(){
        return consultaRepository.findAll();
    }
}
