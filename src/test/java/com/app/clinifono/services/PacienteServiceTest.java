package com.app.clinifono.services;

import com.app.clinifono.configuration.exceptions.UniqueValueException;
import com.app.clinifono.entities.Endereco;
import com.app.clinifono.entities.Paciente;
import com.app.clinifono.repositories.EnderecoRepository;
import com.app.clinifono.repositories.PacienteRepository;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class PacienteServiceTest {

    @Autowired
    PacienteService pacienteService;

    @MockBean
    PacienteRepository pacienteRepository;

    @MockBean
    EnderecoRepository enderecoRepository;

    Paciente paciente;
    Paciente outroPaciente;
    Endereco endereco;

    @BeforeEach
    void setUp() {
        endereco = new Endereco(1L, "Rua Exemplo", "123", "Bairro", "Cidade", "Estado", "12345-678",null);

        paciente = new Paciente(1L, "Mamonha Paciente", "999.999.999-99", LocalDate.of(1997, 1, 3), "+55 45 98416-9058", endereco,null);
        outroPaciente = new Paciente(2L, "Victor TDAH", "888.888.888-88", LocalDate.of(1990, 2, 15), "+55 45 98417-9058", endereco,null);

        Mockito.when(pacienteRepository.save(ArgumentMatchers.any(Paciente.class))).thenReturn(paciente);
        Mockito.when(pacienteRepository.findById(1L)).thenReturn(Optional.ofNullable(paciente));
        Mockito.when(pacienteRepository.findById(2L)).thenReturn(Optional.ofNullable(outroPaciente));
        Mockito.when(pacienteRepository.findAll()).thenReturn(List.of(paciente, outroPaciente));
        Mockito.when(enderecoRepository.save(ArgumentMatchers.any(Endereco.class))).thenReturn(endereco);
    }

    @Test
    @DisplayName("Teste - Save")
    void cenario01() {
        var resultado = pacienteService.save(paciente);
        Assertions.assertEquals(paciente, resultado);
        Assertions.assertEquals(paciente.getNome(), resultado.getNome());
    }

    @Test
    @DisplayName("Teste - ExceptionSave")
    void cenario02() {
        Mockito.when(pacienteRepository.save(ArgumentMatchers.any(Paciente.class)))
                .thenThrow(new DataIntegrityViolationException("Cpf já cadastrado"));

        var exception = Assertions.assertThrows(UniqueValueException.class, () -> {
            pacienteService.save(paciente);
        });

        Assertions.assertEquals("Cpf já cadastrado", exception.getMessage());
    }

    @Test
    @DisplayName("Teste - Update")
    void cenario03() {
        paciente.setNome("Mamonha Atualizado");

        Mockito.when(pacienteRepository.save(paciente)).thenReturn(paciente);

        var atualizado = pacienteService.update(paciente, 1L);

        Assertions.assertEquals(1L, atualizado.getId());
        Assertions.assertEquals("Mamonha Atualizado", atualizado.getNome());
    }

    @Test
    @DisplayName("Teste - ExceptionUpdate")
    void cenario04() {
        Mockito.when(pacienteRepository.save(ArgumentMatchers.any(Paciente.class)))
                .thenThrow(new DataIntegrityViolationException("Cpf já cadastrado"));

        var exception = Assertions.assertThrows(UniqueValueException.class, () -> {
            pacienteService.update(paciente, 1L);
        });

        Assertions.assertEquals("Cpf já cadastrado!", exception.getMessage());
    }

    @Test
    @DisplayName("Teste - findById")
    void cenario05() {
        var resultado = pacienteService.findById(1L);

        Assertions.assertEquals(paciente, resultado);
    }

    @Test
    @DisplayName("Teste - findAll")
    void cenario06() {
        List<Paciente> pacientes = pacienteService.findAll();

        Assertions.assertEquals(2, pacientes.size());
        Assertions.assertEquals("Mamonha Paciente", pacientes.get(0).getNome());
        Assertions.assertEquals("Victor TDAH", pacientes.get(1).getNome());
    }

    @Test
    @DisplayName("Teste - Delete")
    void cenario07() {
        pacienteService.delete(1L);
        Mockito.verify(pacienteRepository, Mockito.times(1)).deleteById(1L);
    }
}

