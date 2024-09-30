package com.app.clinifono.controllers;

import com.app.clinifono.dto.endereco.EnderecoDto;
import com.app.clinifono.dto.paciente.PacienteDto;
import com.app.clinifono.dto.paciente.PacienteUpdateDto;
import com.app.clinifono.dto.paciente.ResponsePacienteDto;
import com.app.clinifono.entities.Endereco;
import com.app.clinifono.entities.Paciente;
import com.app.clinifono.repositories.EnderecoRepository;
import com.app.clinifono.repositories.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class PacienteControllerTest {

    @MockBean
    private PacienteRepository pacienteRepository;

    @Autowired
    private PacienteController pacienteController;

    @MockBean
    private EnderecoRepository enderecoRepository;

    private Paciente pacienteEntity;
    @BeforeEach
    void setUp() {
        pacienteEntity = new Paciente(
                1L,
                "João Silva",
                "123.456.789-00",  // CPF
                LocalDate.of(1990, 5, 15),
                "+55 11 91234-5678",
                null,
                new ArrayList<>()
        );
    }

    @Test
    void testCreate() {
        EnderecoDto endereco = new EnderecoDto(
                "Rua das Flores",
                "12345-678",
                "Jardim das Palmeiras",
                "São Paulo",
                "São Paulo",
                "123"
        );
        PacienteDto pacienteDto = new PacienteDto(
                "Paciente Teste",
                "123.456.789-00",
                LocalDate.of(1990, 5, 15),
                "+55 11 99999-9999",
                 endereco
                );

        when(pacienteRepository.save(any(Paciente.class))).thenReturn(pacienteEntity);
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(new Endereco());
        ResponseEntity<ResponsePacienteDto> response = pacienteController.create(pacienteDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(pacienteEntity.getNome(), response.getBody().nome());
    }

    @Test
    void testUpdate() {
        PacienteUpdateDto updateDto = new PacienteUpdateDto("Paciente sem nome", "987.654.321-00", LocalDate.of(1990, 5, 15), "+55 11 91234-5678");
        when(pacienteRepository.findById(1l)).thenReturn(Optional.of(pacienteEntity));
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(pacienteEntity);

        ResponseEntity<ResponsePacienteDto> response = pacienteController.update(1L, updateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("João Silva", response.getBody().nome());
    }

    @Test
    void testDelete() {
        doNothing().when(pacienteRepository).deleteById(anyLong());

        ResponseEntity<Void> response = pacienteController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(pacienteRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindById() {
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(pacienteEntity));

        ResponseEntity<Paciente> response = pacienteController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("João Silva", response.getBody().getNome());
    }

    @Test
    void testFindAll() {
        List<Paciente> pacientes = new ArrayList<>();
        pacientes.add(pacienteEntity);
        when(pacienteRepository.findAll()).thenReturn(pacientes);
        ResponseEntity<List<Paciente>> response = pacienteController.findall();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("João Silva", response.getBody().get(0).getNome());
    }
}