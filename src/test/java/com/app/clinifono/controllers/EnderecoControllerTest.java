package com.app.clinifono.controllers;

import com.app.clinifono.dto.endereco.EnderecoUpdateDto;
import com.app.clinifono.dto.endereco.ResponseEnderecoDto;
import com.app.clinifono.entities.Endereco;
import com.app.clinifono.entities.Paciente;
import com.app.clinifono.repositories.EnderecoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class EnderecoControllerTest {

    @MockBean
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoController enderecoController;

    private Endereco enderecoEntity;

    @BeforeEach
    void setUp() {
        enderecoEntity = new Endereco(
                1L,
                "Rua das Flores",
                "12345-678",
                "Jardim das Palmeiras",
                "SP",
                "São Paulo",
                "123",
                new Paciente()
        );
    }

    @Test
    void testCreate() {
        EnderecoUpdateDto enderecoUpdateDto = new EnderecoUpdateDto(
                "Rua das Flores",
                "12345-678",
                "Jardim das Palmeiras",
                "São Paulo",
                "São Paulo",
                "123"
        );

        when(enderecoRepository.save(any(Endereco.class))).thenReturn(enderecoEntity);

        ResponseEntity<ResponseEnderecoDto> response = enderecoController.create(enderecoUpdateDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(enderecoEntity.getNomeRua(), response.getBody().nomeRua());
        verify(enderecoRepository, times(1)).save(any(Endereco.class));
    }

    @Test
    void testUpdate() {
        EnderecoUpdateDto updateDto = new EnderecoUpdateDto(
                "Rua Atualizada",
                "98765-432",
                "Bairro Atualizado",
                "São Paulo",
                "São Paulo",
                "321"
        );

        when(enderecoRepository.findById(anyLong())).thenReturn(Optional.of(enderecoEntity));
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(enderecoEntity);

        ResponseEntity<ResponseEnderecoDto> response = enderecoController.update(1L, updateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(enderecoEntity.getNomeRua(), response.getBody().nomeRua());
        verify(enderecoRepository, times(1)).findById(1L);
        verify(enderecoRepository, times(1)).save(any(Endereco.class));
    }

//    @Test
//    void testDelete() {
//        doNothing().when(enderecoRepository).deleteById(anyLong());
//
//        ResponseEntity<Void> response = enderecoController.delete(1L);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        verify(enderecoRepository, times(1)).deleteById(1L);
//    }

    @Test
    void testFindById() {
        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(enderecoEntity));

        ResponseEntity<ResponseEnderecoDto> response = enderecoController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(enderecoEntity.getNomeRua(), response.getBody().nomeRua());
        verify(enderecoRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAll() {
        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(enderecoEntity);
        when(enderecoRepository.findAll()).thenReturn(enderecos);

        ResponseEntity<List<ResponseEnderecoDto>> response = enderecoController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(enderecoEntity.getNomeRua(), response.getBody().get(0).nomeRua());
        verify(enderecoRepository, times(1)).findAll();
    }
}