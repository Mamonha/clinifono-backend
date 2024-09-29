package com.app.clinifono.services;

import com.app.clinifono.configuration.exceptions.PasswordMissmatchException;
import com.app.clinifono.configuration.exceptions.UniqueValueException;
import com.app.clinifono.entities.Usuarios;
import com.app.clinifono.repositories.UsuariosRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    UsuarioService usuarioService;

    @MockBean
    UsuariosRepository usuariosRepository;

    Usuarios usuario;
    Usuarios outroUsuario;

    @BeforeEach
    void setUP(){
        usuario = new Usuarios(1L,"Mamonha cardoso", "mamonha@gmail.com", "+55 45 98416-9058","senha123",null);
        outroUsuario = new Usuarios(1L,"Victor TDAH", "tdah@gmail.com", "+55 45 98417-9058","senha123",null);

        Mockito.when(usuariosRepository.save(ArgumentMatchers.any(Usuarios.class))).thenReturn(usuario);
        Mockito.when(usuariosRepository.findById(1L)).thenReturn(Optional.ofNullable(usuario));
        Mockito.when(usuariosRepository.findById(2L)).thenReturn(Optional.ofNullable(outroUsuario));
        Mockito.when(usuariosRepository.findAll()).thenReturn(List.of(usuario, outroUsuario));

    }

    @Test
    @DisplayName("Teste - Save")
    void cenario01(){
        var resultado = usuarioService.create(usuario);
        Assertions.assertEquals(usuario, resultado );
        Assertions.assertEquals(usuario.getNome(), resultado.getNome());

    }

    @Test
    @DisplayName("Teste - ExceptionSave")
    void cenario02(){
        Mockito.when(usuariosRepository.save(ArgumentMatchers.any(Usuarios.class)))
                .thenThrow(new DataIntegrityViolationException("Valor único violado"));

        var exception = Assertions.assertThrows(UniqueValueException.class, () -> {
            usuarioService.create(usuario);
        });

        Assertions.assertEquals("Email ou Numero de telefone já cadastrados", exception.getMessage());
    }

    @Test
    @DisplayName("Teste - Update")
    void cenario03(){
        usuario.setNome("Mamonha atualizado");

        Mockito.when(usuariosRepository.save(usuario)).thenReturn(usuario);

        var atualizAnderson = usuarioService.update(usuario, 1L);

        assertEquals(1L, atualizAnderson.getId());
        assertEquals("Mamonha atualizado", atualizAnderson.getNome());
    }

    @Test
    @DisplayName("Teste - ExcepctionUpdate")
    void cenario04(){
        Mockito.when(usuariosRepository.save(ArgumentMatchers.any(Usuarios.class))).thenThrow(new DataIntegrityViolationException("Valor Único violado"));

        var exception = Assertions.assertThrows(UniqueValueException.class, () -> {
            usuarioService.update(usuario, 1L);
        });

        Assertions.assertEquals("Email ou Numero de telefone já cadastrados", exception.getMessage());
    }

    @Test
    @DisplayName("Teste - findById")
    void cenario05(){
        var resultado = usuarioService.findById(1L);

        Assertions.assertEquals(usuario, resultado);
    }

    @Test
    @DisplayName("Teste - findAll")
    void cenario06() {
        List<Usuarios> usuarios = usuarioService.findALl();

        assertEquals(2, usuarios.size());
        assertEquals("Mamonha cardoso", usuarios.get(0).getNome());
        assertEquals("Victor TDAH", usuarios.get(1).getNome());
    }

    @Test
    @DisplayName("Teste - atualizar Senha")
    void cenario07(){
        var senhaAtual = "senha123";
        var novaSenha = "novasenha123";

        usuarioService.atualizarSenha(1L ,senhaAtual ,novaSenha);
        Assertions.assertEquals(novaSenha, usuario.getSenha());
    }

    @Test
    @DisplayName("Teste - ExceptionSenha")
    void cenario08(){
        var senhaAtualIncorreta = "senhaerrada123";
        var novaSenha = "novasenha123";

        PasswordMissmatchException exception = Assertions.assertThrows(PasswordMissmatchException.class, () -> {
            usuarioService.atualizarSenha(1L, senhaAtualIncorreta, novaSenha);
        });

        Assertions.assertEquals("A senha atual está incorreta", exception.getMessage());
        Mockito.verify(usuariosRepository, Mockito.never()).save(usuario);
    }




}
