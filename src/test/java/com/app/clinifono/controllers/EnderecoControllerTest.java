package com.app.clinifono.controllers;

import com.app.clinifono.dto.endereco.EnderecoUpdateDto;
import com.app.clinifono.dto.endereco.ResponseEnderecoDto;
import com.app.clinifono.entities.Endereco;
import com.app.clinifono.mapper.EnderecoMapper;
import com.app.clinifono.services.EnderecoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnderecoControllerTest {

    @Mock
    private EnderecoService enderecoService;

    @Mock
    private EnderecoMapper enderecoMapper;

    @InjectMocks
    private EnderecoController enderecoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateEndereco() {
        EnderecoUpdateDto updateDto = new EnderecoUpdateDto(
                "Rua Teste",
                "12345-678",
                "Bairro Teste",
                "Estado Teste",
                "Cidade Teste",
                "123"
        );
        Endereco endereco = new Endereco();
        ResponseEnderecoDto responseDto = new ResponseEnderecoDto(
                1L,
                "Rua Teste",
                "12345-678",
                "Bairro Teste",
                "Estado Teste",
                "Cidade Teste",
                "123"
        );
        when(enderecoService.update(anyLong(), any(Endereco.class))).thenReturn(endereco);
        when(enderecoMapper.toUpdateEntity(updateDto)).thenReturn(endereco);
        when(enderecoMapper.toDto(endereco)).thenReturn(responseDto);
        ResponseEntity<ResponseEnderecoDto> response = enderecoController.update(1L, updateDto);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testCreateEndereco() {
        EnderecoUpdateDto enderecoDto = new EnderecoUpdateDto(
                "Rua Nova",
                "12345-678",
                "Bairro Novo",
                "Estado Novo",
                "Cidade Nova",
                "456"
        );
        Endereco enderecoEntity = new Endereco();
        ResponseEnderecoDto responseDto = new ResponseEnderecoDto(
                1L,
                "Rua Nova",
                "12345-678",
                "Bairro Novo",
                "Estado Novo",
                "Cidade Nova",
                "456"
        );
        when(enderecoService.save(any(Endereco.class))).thenReturn(enderecoEntity);
        when(enderecoMapper.toUpdateEntity(enderecoDto)).thenReturn(enderecoEntity);
        when(enderecoMapper.toDto(enderecoEntity)).thenReturn(responseDto);
        ResponseEntity<ResponseEnderecoDto> response = enderecoController.create(enderecoDto);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testFindById() {
        Endereco enderecoEntity = new Endereco();
        ResponseEnderecoDto responseDto = new ResponseEnderecoDto(
                1L,
                "Rua Exemplo",
                "12345-678",
                "Bairro Exemplo",
                "Estado Exemplo",
                "Cidade Exemplo",
                "789"
        );
        when(enderecoService.findById(1L)).thenReturn(enderecoEntity);
        when(enderecoMapper.toDto(enderecoEntity)).thenReturn(responseDto);
        ResponseEntity<ResponseEnderecoDto> response = enderecoController.findById(1L);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testFindAllEnderecos() {
        List<Endereco> enderecos = List.of(new Endereco(), new Endereco());
        List<ResponseEnderecoDto> responseDtos = List.of(
                new ResponseEnderecoDto(1L, "Rua 1", "12345-678", "Bairro 1", "Estado 1", "Cidade 1", "1"),
                new ResponseEnderecoDto(2L, "Rua 2", "98765-432", "Bairro 2", "Estado 2", "Cidade 2", "2")
        );

        when(enderecoService.findAll()).thenReturn(enderecos);
        when(enderecoMapper.toDto(any(Endereco.class))).thenReturn(responseDtos.get(0), responseDtos.get(1));

        ResponseEntity<List<ResponseEnderecoDto>> response = enderecoController.findAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }
}
