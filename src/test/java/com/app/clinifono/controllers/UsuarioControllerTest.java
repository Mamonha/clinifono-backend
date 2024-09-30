package com.app.clinifono.controllers;

import com.app.clinifono.dto.usuario.*;
import com.app.clinifono.entities.Paciente;
import com.app.clinifono.entities.Usuarios;
import com.app.clinifono.repositories.UsuariosRepository;
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
import static org.mockito.Mockito.*;

@SpringBootTest
class UsuarioControllerTest {

    @MockBean
    private UsuariosRepository usuariosRepository;

    @Autowired
    private UsuarioController usuarioController;
    private Usuarios usuarioEntity ;
    @BeforeEach
    void setUp() {
        usuarioEntity = new Usuarios(1L, "Nome Teste", "teste@exemplo.com", "+55 11 91234-5678", "senha123", new ArrayList<>());
    }

    @Test
    void testSave() {
        UsuarioDto usuarioDto = new UsuarioDto(
                "Nome Teste",
                "teste@exemplo.com",
                "+55 11 91234-5678",
                "senha123");
        when(usuariosRepository.save(any(Usuarios.class))).thenReturn(usuarioEntity);
        ResponseEntity<ResponseUsuarioDto> response = usuarioController.save(usuarioDto);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(usuarioEntity.getNome(), response.getBody().nome());
    }

    @Test
    void testUpdatePassword() {
        UsuarioSenhaUpdateDto senhaUpdateDto = new UsuarioSenhaUpdateDto("senha123", "novaSenha123");

        when(usuariosRepository.save(any())).thenReturn(new Usuarios());
        when(usuariosRepository.findById(1L)).thenReturn(Optional.ofNullable(usuarioEntity));

        ResponseEntity<Void> response = usuarioController.updatePassword(senhaUpdateDto, 1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testUpdate() {
        UsuarioUpdateDto updateDto = new UsuarioUpdateDto("Nome Atualizado", "novoemail@exemplo.com", "+55 11 99999-9999");
        when(usuariosRepository.findById(2L)).thenReturn(Optional.of(usuarioEntity));
        when(usuariosRepository.save(any(Usuarios.class))).thenReturn(usuarioEntity);

        ResponseEntity<ResponseUsuarioDto> response = usuarioController.update(2L, updateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Nome Teste", response.getBody().nome());
    }

    @Test
    void testFindById() {
        when(usuariosRepository.findById(1L)).thenReturn(Optional.of(usuarioEntity));

        ResponseEntity<ResponseUsuarioDto> response = usuarioController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Nome Teste", response.getBody().nome());
    }

    @Test
    void testFindAll() {
        List<Usuarios> usuariosList = new ArrayList<>();
        usuariosList.add(usuarioEntity);
        when(usuariosRepository.findAll()).thenReturn(usuariosList);

        ResponseEntity<List<ResponseUsuarioDto>> response = usuarioController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Nome Teste", response.getBody().get(0).nome());
    }
}