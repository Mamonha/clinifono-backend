package com.app.clinifono.services;

import com.app.clinifono.configuration.exceptions.BusinessException;
import com.app.clinifono.configuration.exceptions.EntityNotFoundException;
import com.app.clinifono.entities.Consulta;
import com.app.clinifono.entities.Paciente;
import com.app.clinifono.repositories.ConsultaRepository;
import com.app.clinifono.repositories.PacienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PacienteRepository pacienteRepository;


    @Transactional
    public Consulta save(Consulta consulta){

        if(!consulta.getDataAgendamento().isAfter(LocalDate.now())){
            throw new BusinessException("A data é inválida");
        }
        if(consulta.getHoraDeInicio().isAfter(consulta.getHoraDoFim())) {
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
        if(consulta.getHoraDeInicio().isAfter(consulta.getHoraDoFim())) {
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

        if (consulta.getStatus() == null) {
            throw new BusinessException("Erro ao confirmar a consulta devido a má formatação do status");
        }

        update.setStatus(consulta.getStatus());
        return consultaRepository.save(update);

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
