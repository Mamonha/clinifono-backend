package com.app.clinifono.controllers;

import com.app.clinifono.dto.consulta.ConsultaConfirmDto;
import com.app.clinifono.dto.consulta.ConsultaDto;
import com.app.clinifono.dto.consulta.ConsultaUpdateDto;
import com.app.clinifono.dto.consulta.ResponseConsultaDto;
import com.app.clinifono.dto.paciente.PacienteIntegrationDto;
import com.app.clinifono.dto.usuario.UsuarioIntegrationDto;
import com.app.clinifono.entities.Consulta;
import com.app.clinifono.entities.Paciente;
import com.app.clinifono.entities.Status;
import com.app.clinifono.entities.Usuarios;
import com.app.clinifono.repositories.ConsultaRepository;
import com.app.clinifono.repositories.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class ConsultaControllerTest {

    @MockBean
    private ConsultaRepository consultaRepository;

    @MockBean
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaController consultaController;

    private Consulta consultaEntity;

    private Paciente paciente;

    @BeforeEach
    void setUp() {
        Usuarios usuarios= new Usuarios();
        usuarios.setId(1L);
        paciente= new Paciente(
                1L,
                "João Silva",
                "123.456.789-00",  // CPF
                LocalDate.of(1990, 5, 15),
                "+55 11 91234-5678",
                null,
                new ArrayList<>()
        );
         consultaEntity = new Consulta(
                1L,  // ID da consulta
                LocalDate.of(2024, 10, 15),  // Data de agendamento
                LocalTime.of(14, 30),  // Hora de início
                LocalTime.of(15, 30),  // Hora de fim
                "Consulta de rotina",  // Descrição da consulta
                Status.PENDING,  // Status da consulta
                new Usuarios(),  // Usuário associado
                paciente // Paciente associado
        );
        when(consultaRepository.findById(1L)).thenReturn(Optional.ofNullable(consultaEntity));
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consultaEntity);


    }

    @Test
    void testCreate() {
        ConsultaDto consultaDto = new ConsultaDto(LocalDate.of(2024, 10, 15), LocalTime.of(14, 30), LocalTime.of(15, 30),"description", new UsuarioIntegrationDto(1L), new PacienteIntegrationDto(1L));
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consultaEntity);

        ResponseEntity<ResponseConsultaDto> response = consultaController.create(consultaDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(consultaEntity.getId(), response.getBody().id());
    }

    @Test
    void testUpdate() {
        ConsultaUpdateDto updateDto = new ConsultaUpdateDto(LocalDate.of(2024, 10, 15), LocalTime.of(14, 30), LocalTime.of(15, 30), "description");
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consultaEntity);
        ResponseEntity<ResponseConsultaDto> response = consultaController.update(1L, updateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(consultaEntity.getId(), response.getBody().id());
    }

    @Test
    void testConfirmar() {
        ConsultaConfirmDto confirmDto = new ConsultaConfirmDto(Status.CONFIRMED);
        ResponseEntity<ResponseConsultaDto> response = consultaController.confirmar(1L, confirmDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(consultaEntity.getId(), response.getBody().id());
    }

    @Test
    void testDelete() {
        doNothing().when(consultaRepository).deleteById(anyLong());

        ResponseEntity<Void> response = consultaController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testFindById() {
        ResponseEntity<ResponseConsultaDto> response = consultaController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(consultaEntity.getId(), response.getBody().id());
    }

    @Test
    void testFindAll() {
        List<Consulta> consultas = new ArrayList<>();
        consultas.add(consultaEntity);
        when(consultaRepository.findAll()).thenReturn(consultas);
        ResponseEntity<List<ResponseConsultaDto>> response = consultaController.findall();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(consultaEntity.getId(), response.getBody().get(0).id());
    }
}