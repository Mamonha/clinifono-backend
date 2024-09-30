package com.app.clinifono.controllers;

import com.app.clinifono.dto.usuario.ResponseUsuarioDto;
import com.app.clinifono.dto.usuario.UsuarioDto;
import com.app.clinifono.dto.usuario.UsuarioSenhaUpdateDto;
import com.app.clinifono.dto.usuario.UsuarioUpdateDto;
import com.app.clinifono.entities.Usuarios;
import com.app.clinifono.mapper.UserMapper;
import com.app.clinifono.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        // Dados de exemplo
        UsuarioDto usuarioDto = new UsuarioDto("Nome Teste", "teste@exemplo.com", "+55 11 91234-5678", "senha123");
        Usuarios usuarioEntity = new Usuarios();
        ResponseUsuarioDto responseUsuarioDto = new ResponseUsuarioDto(1L, "Nome Teste", "teste@exemplo.com", "+55 11 91234-5678", List.of());

        // Mockando o comportamento do mapper e service
        when(userMapper.toEntity(usuarioDto)).thenReturn(usuarioEntity);
        when(usuarioService.create(any(Usuarios.class))).thenReturn(usuarioEntity);
        when(userMapper.toDto(usuarioEntity)).thenReturn(responseUsuarioDto);

        // Chamada do método da controladora
        ResponseEntity<ResponseUsuarioDto> response = usuarioController.save(usuarioDto);

        // Validações
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseUsuarioDto, response.getBody());
    }

    @Test
    void testUpdatePassword() {
        UsuarioSenhaUpdateDto updateDto = new UsuarioSenhaUpdateDto("senha123", "novaSenha123");

        // Chamada do método da controladora
        ResponseEntity<Void> response = usuarioController.updatePassword(updateDto, 1L);

        // Verificar se o serviço foi chamado com os parâmetros corretos
        verify(usuarioService, times(1)).atualizarSenha(1L, "senha123", "novaSenha123");

        // Validações
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testUpdate() {
        UsuarioUpdateDto updateDto = new UsuarioUpdateDto("Nome Atualizado", "atualizado@exemplo.com", "+55 11 91234-9999");
        Usuarios usuario = new Usuarios();
        ResponseUsuarioDto responseDto = new ResponseUsuarioDto(1L, "Nome Atualizado", "atualizado@exemplo.com", "+55 11 91234-9999", List.of());

        // Mockando o comportamento do service e do mapper
        when(usuarioService.update(any(Usuarios.class), eq(1L))).thenReturn(usuario);
        when(userMapper.updateDto(updateDto)).thenReturn(usuario);
        when(userMapper.toDto(usuario)).thenReturn(responseDto);

        // Chamada do método da controladora
        ResponseEntity<ResponseUsuarioDto> response = usuarioController.update(1L, updateDto);

        // Validações
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testFindById() {
        Usuarios usuario = new Usuarios();
        ResponseUsuarioDto responseDto = new ResponseUsuarioDto(1L, "Nome Teste", "teste@exemplo.com", "+55 11 91234-5678", List.of());

        // Mockando o comportamento do service e mapper
        when(usuarioService.findById(1L)).thenReturn(usuario);
        when(userMapper.toDto(usuario)).thenReturn(responseDto);

        // Chamada do método da controladora
        ResponseEntity<ResponseUsuarioDto> response = usuarioController.findById(1L);

        // Validações
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testFindAll() {
        List<Usuarios> usuarios = List.of(new Usuarios(), new Usuarios());
        List<ResponseUsuarioDto> responseDtoList = usuarios.stream()
                .map(usuario -> new ResponseUsuarioDto(1L, "Nome", "email@teste.com", "+55 11 91234-5678", List.of()))
                .collect(Collectors.toList());

        // Mockando o comportamento do service e mapper
        when(usuarioService.findALl()).thenReturn(usuarios);
        when(userMapper.toDto(any(Usuarios.class))).thenReturn(new ResponseUsuarioDto(1L, "Nome", "email@teste.com", "+55 11 91234-5678", List.of()));

        // Chamada do método da controladora
        ResponseEntity<List<ResponseUsuarioDto>> response = usuarioController.findAll();

        // Validações
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDtoList.size(), response.getBody().size());
    }
}