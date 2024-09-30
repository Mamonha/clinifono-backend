package com.app.clinifono.services;

import com.app.clinifono.configuration.exceptions.EntityNotFoundException;
import com.app.clinifono.configuration.exceptions.UniqueValueException;
import com.app.clinifono.entities.Endereco;
import com.app.clinifono.repositories.EnderecoRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EnderecoServiceTest {

    @Autowired
    EnderecoService enderecoService;

    @MockBean
    EnderecoRepository enderecoRepository;

    Endereco endereco;
    Endereco outroEndereco;

    @BeforeEach
    void setUp() {
        endereco = new Endereco(1L, "Rua ABC", "12345-678", "Centro", "SP", "São Paulo", "123",null);
        outroEndereco = new Endereco(2L, "Rua XYZ", "98765-432", "Bairro B", "RJ", "Rio de Janeiro", "456",null);

        Mockito.when(enderecoRepository.save(ArgumentMatchers.any(Endereco.class))).thenReturn(endereco);
        Mockito.when(enderecoRepository.findById(1L)).thenReturn(Optional.ofNullable(endereco));
        Mockito.when(enderecoRepository.findById(2L)).thenReturn(Optional.ofNullable(outroEndereco));
        Mockito.when(enderecoRepository.findAll()).thenReturn(List.of(endereco, outroEndereco));
    }

    @Test
    @DisplayName("Teste - Save")
    void cenario01() {
        var resultado = enderecoService.save(endereco);
        assertEquals(endereco, resultado);
        assertEquals(endereco.getNomeRua(), resultado.getNomeRua());
    }



    @Test
    @DisplayName("Teste - Update")
    void cenario03() {
        endereco.setNomeRua("Rua Atualizada");

        Mockito.when(enderecoRepository.save(endereco)).thenReturn(endereco);

        var atualizado = enderecoService.update(1L, endereco);

        assertEquals(1L, atualizado.getId());
        assertEquals("Rua Atualizada", atualizado.getNomeRua());
    }

    @Test
    @DisplayName("Teste - ExceptionUpdate")
    void cenario04() {
        Mockito.when(enderecoRepository.save(ArgumentMatchers.any(Endereco.class)))
                .thenThrow(new DataIntegrityViolationException("Valor único violado"));

        var exception = Assertions.assertThrows(UniqueValueException.class, () -> {
            enderecoService.update(1L, endereco);
        });

        assertEquals("Erro ao atualizar o endereço. Verifique se algum campo está duplicado.", exception.getMessage());
    }

    @Test
    @DisplayName("Teste - findById")
    void cenario05() {
        var resultado = enderecoService.findById(1L);

        assertEquals(endereco, resultado);
    }

    @Test
    @DisplayName("Teste - Exception findById")
    void cenario06() {
        Mockito.when(enderecoRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        var exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            enderecoService.findById(100L);
        });

        assertEquals("Endereco não encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("Teste - findAll")
    void cenario07() {
        List<Endereco> enderecos = enderecoService.findAll();

        assertEquals(2, enderecos.size());
        assertEquals("Rua ABC", enderecos.get(0).getNomeRua());
        assertEquals("Rua XYZ", enderecos.get(1).getNomeRua());
    }
}
