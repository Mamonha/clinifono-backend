package com.app.clinifono.services;

import com.app.clinifono.configuration.exceptions.BusinessException;
import com.app.clinifono.configuration.exceptions.EntityNotFoundException;
import com.app.clinifono.entities.Consulta;
import com.app.clinifono.repositories.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Transactional
    public Consulta save(Consulta consulta){

        if(!consulta.getDataAgendamento().isAfter(LocalDate.now())){
            throw new BusinessException("A data é inválida");
        }
        if(!consulta.getHoraDeInicio().isAfter(consulta.getHoraDoFim())) {
            throw new BusinessException("Hora de encerramento invalida");
        }
        return consultaRepository.save(consulta);
    }

    @Transactional
    public Consulta update(Consulta consulta, Long id){
        var update = findById(id);

        if(!consulta.getDataAgendamento().isAfter(LocalDate.now())){
            throw new BusinessException("A data é inválida");
        }
        if(!consulta.getHoraDeInicio().isAfter(consulta.getHoraDoFim())) {
            throw new BusinessException("Hora de encerramento invalida");
        }
        if (consulta.getDataAgendamento() != null){
            update.setDataAgendamento(consulta.getDataAgendamento());
        }
        if (consulta.getHoraDeInicio() != null){
            update.setHoraDeInicio(consulta.getHoraDeInicio());
        }
        if (consulta.getHoraDoFim() != null){
            update.setHoraDoFim(consulta.getHoraDoFim());
        }
        if (consulta.getDataAgendamento() != null){
            update.setDataAgendamento(consulta.getDataAgendamento());
        }
        if(!consulta.getDescricao().isBlank() && consulta.getDescricao() != null) {
            update.setDescricao(consulta.getDescricao());
        }
        return consultaRepository.save(update);
    }

    @Transactional
    public Consulta confirmarConsulta(Consulta consulta, Long id){
        var update = findById(id);
        try {
            update.setStatus(consulta.getStatus());
            return consultaRepository.save(update);
        } catch (RuntimeException ex){
            throw new BusinessException("Erro ao confirmar a consulta devido a má formatação do status");
        }

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
