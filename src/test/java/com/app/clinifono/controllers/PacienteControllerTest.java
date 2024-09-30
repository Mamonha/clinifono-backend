package com.app.clinifono.controllers;

import com.app.clinifono.dto.paciente.PacienteDto;
import com.app.clinifono.dto.paciente.PacienteUpdateDto;
import com.app.clinifono.dto.paciente.ResponsePacienteDto;
import com.app.clinifono.entities.Paciente;
import com.app.clinifono.mapper.PacienteMapper;
import com.app.clinifono.services.PacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PacienteControllerTest {

    @Mock
    private PacienteService pacienteService;

    @Mock
    private PacienteMapper pacienteMapper;

    @InjectMocks
    private PacienteController pacienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePaciente() {
        PacienteDto pacienteDto = new PacienteDto(
                "Nome Teste",
                "123.456.789-00",
                null,
                "+55 11 91234-5678",
                null
        );
        Paciente pacienteEntity = new Paciente();
        ResponsePacienteDto responsePacienteDto = new ResponsePacienteDto(
                1L,
                "Nome Teste",
                "123.456.789-00",
                null,
                "+55 11 91234-5678",
                null
        );
        when(pacienteMapper.toEntity(pacienteDto)).thenReturn(pacienteEntity);
        when(pacienteService.save(any(Paciente.class))).thenReturn(pacienteEntity);
        when(pacienteMapper.toDto(pacienteEntity)).thenReturn(responsePacienteDto);
        ResponseEntity<ResponsePacienteDto> response = pacienteController.create(pacienteDto);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responsePacienteDto, response.getBody());
    }

    @Test
    void testUpdatePaciente() {
        PacienteUpdateDto updateDto = new PacienteUpdateDto(
                "Nome Atualizado",
                "123.456.789-00",
                null,
                "+55 11 91234-9999"
        );
        Paciente pacienteEntity = new Paciente();
        ResponsePacienteDto responseDto = new ResponsePacienteDto(
                1L,
                "Nome Atualizado",
                "123.456.789-00",
                null,
                "+55 11 91234-9999",
                null
        );
        when(pacienteService.update(any(Paciente.class), eq(1L))).thenReturn(pacienteEntity);
        when(pacienteMapper.toUpdateEntity(updateDto)).thenReturn(pacienteEntity);
        when(pacienteMapper.toDto(pacienteEntity)).thenReturn(responseDto);
        ResponseEntity<ResponsePacienteDto> response = pacienteController.update(1L, updateDto);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testDeletePaciente() {
        ResponseEntity<Void> response = pacienteController.delete(1L);
        verify(pacienteService, times(1)).delete(1L);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testFindById() {
        Paciente pacienteEntity = new Paciente();
        pacienteEntity.setId(1L);
        when(pacienteService.findById(1L)).thenReturn(pacienteEntity);
        ResponseEntity<Paciente> response = pacienteController.findById(1L);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pacienteEntity, response.getBody());
    }

    @Test
    void testFindAllPacientes() {
        List<Paciente> pacientes = List.of(new Paciente(), new Paciente());
        when(pacienteService.findAll()).thenReturn(pacientes);
        ResponseEntity<List<Paciente>> response = pacienteController.findall();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pacientes.size(), response.getBody().size());
    }
}
