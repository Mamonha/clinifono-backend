package com.app.clinifono.services;

import com.app.clinifono.configuration.exceptions.BusinessException;
import com.app.clinifono.entities.Consulta;
import com.app.clinifono.entities.Paciente;
import com.app.clinifono.entities.Status;
import com.app.clinifono.repositories.ConsultaRepository;
import com.app.clinifono.repositories.PacienteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
public class ConsultaServiceTest {

    @Autowired
    ConsultaService consultaService;

    @MockBean
    ConsultaRepository consultaRepository;

    @MockBean
    PacienteRepository pacienteRepository;

    @MockBean
    RestTemplate restTemplate;

    Consulta consulta;
    Consulta outraConsulta;
    Paciente paciente;

    @BeforeEach
    void setUp() {
        paciente = new Paciente(1L, "João Silva", "12345678900", LocalDate.of(1985, 5, 20), "(11) 98765-4321", null, null);
        consulta = new Consulta(1L, LocalDate.of(2025, 9, 25), LocalTime.of(14, 0), LocalTime.of(15, 0), "Consulta de rotina", Status.PENDING, null, paciente);
        outraConsulta = new Consulta(2L, LocalDate.of(2025, 9, 26), LocalTime.of(16, 0), LocalTime.of(17, 0), "Consulta de retorno", Status.CONFIRMED, null, paciente);
        Mockito.when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));
        Mockito.when(consultaRepository.save(ArgumentMatchers.any(Consulta.class))).thenReturn(consulta);
        Mockito.when(consultaRepository.findById(1L)).thenReturn(Optional.of(consulta));
        Mockito.when(consultaRepository.findById(2L)).thenReturn(Optional.of(outraConsulta));
        Mockito.when(consultaRepository.findAll()).thenReturn(List.of(consulta, outraConsulta));
    }

    @Test
    @DisplayName("Teste - Save")
    void cenario01() {
        var resultado = consultaService.save(consulta);
        Assertions.assertEquals(consulta, resultado);
        Assertions.assertEquals(consulta.getDescricao(), resultado.getDescricao());
    }

    @Test
    @DisplayName("Teste - Exception Save Data Inválida")
    void cenario02() {
        consulta.setDataAgendamento(LocalDate.of(2022, 9, 25)); // Data inválida no passado

        var exception = Assertions.assertThrows(BusinessException.class, () -> {
            consultaService.save(consulta);
        });

        Assertions.assertEquals("A data é inválida", exception.getMessage());
    }

    @Test
    @DisplayName("Teste - Exception Save Hora de Encerramento Inválida")
    void cenario03() {
        consulta.setDataAgendamento(LocalDate.now().plusDays(1)); // Data futura
        consulta.setHoraDeInicio(LocalTime.of(15, 0));
        consulta.setHoraDoFim(LocalTime.of(14, 0)); // Hora de encerramento inválida

        var exception = Assertions.assertThrows(BusinessException.class, () -> {
            consultaService.save(consulta);
        });

        Assertions.assertEquals("Hora de encerramento invalida", exception.getMessage());
    }

    @Test
    @DisplayName("Teste - Update")
    void cenario04() {
        consulta.setDescricao("Consulta atualizada");
        consulta.setDataAgendamento(LocalDate.now().plusDays(1)); // Data futura
        consulta.setHoraDeInicio(LocalTime.of(11, 0));
        consulta.setHoraDoFim(LocalTime.of(12, 0)); // Hora de encerramento válida

        var resultado = consultaService.update(consulta, 1L);

        Assertions.assertEquals(1L, resultado.getId());
        Assertions.assertEquals("Consulta atualizada", resultado.getDescricao());
    }

    @Test
    @DisplayName("Teste - Exception Update Data Inválida")
    void cenario05() {
        consulta.setDataAgendamento(LocalDate.of(2022, 9, 25)); // Data inválida

        var exception = Assertions.assertThrows(BusinessException.class, () -> {
            consultaService.update(consulta, 1L);
        });

        Assertions.assertEquals("A data é inválida", exception.getMessage());
    }

    @Test
    @DisplayName("Teste - Confirmar Consulta")
    void cenario06() {
        consulta.setStatus(Status.CONFIRMED);

        var resultado = consultaService.confirmarConsulta(consulta, 1L);
        Assertions.assertEquals(Status.CONFIRMED, resultado.getStatus());
    }

    @Test
    @DisplayName("Teste - Exception Confirmar Consulta")
    void cenario07() {
        consulta.setStatus(null);
        var exception = Assertions.assertThrows(BusinessException.class, () -> {
            consultaService.confirmarConsulta(consulta, 1L);
        });

        Assertions.assertEquals("Erro ao confirmar a consulta devido a má formatação do status", exception.getMessage());
    }

    @Test
    @DisplayName("Teste - FindById")
    void cenario08() {
        var resultado = consultaService.findById(1L);
        Assertions.assertEquals(consulta, resultado);
    }

    @Test
    @DisplayName("Teste - FindAll")
    void cenario09() {
        List<Consulta> consultas = consultaService.findAll();
        Assertions.assertEquals(2, consultas.size());
        Assertions.assertEquals("Consulta de rotina", consultas.get(0).getDescricao());
        Assertions.assertEquals("Consulta de retorno", consultas.get(1).getDescricao());
    }

    @Test
    @DisplayName("Teste - Delete")
    void cenario10() {
        consultaService.delete(1L);
        Mockito.verify(consultaRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Teste - Enviar Consulta para Serviço Externo")
    void cenario11() throws Exception {

        String response = "Consulta enviada com sucesso!";
        Mockito.when(restTemplate.postForObject(
                Mockito.eq("http://localhost:8000/api/consultas"),
                ArgumentMatchers.any(Map.class),
                Mockito.eq(String.class))
        ).thenReturn(response);

        consultaService.sendConsultaToExternalService(consulta);

        ArgumentCaptor<Map<String, Object>> requestCaptor = ArgumentCaptor.forClass(Map.class);
        Mockito.verify(restTemplate, Mockito.times(1))
                .postForObject(Mockito.eq("http://localhost:8000/api/consultas"), requestCaptor.capture(), Mockito.eq(String.class));

        Map<String, Object> capturedRequestBody = requestCaptor.getValue();
        Assertions.assertEquals(consulta.getId(), capturedRequestBody.get("consultaId"));
        Assertions.assertEquals(paciente.getNome(), capturedRequestBody.get("pacienteNome"));
        Assertions.assertEquals(paciente.getTelefone(), capturedRequestBody.get("telefone"));
        Assertions.assertEquals(consulta.getDataAgendamento().toString(), capturedRequestBody.get("dataAgendamento"));
        Assertions.assertEquals(consulta.getHoraDeInicio().toString(), capturedRequestBody.get("horaDeInicio"));
        Assertions.assertEquals(consulta.getHoraDoFim().toString(), capturedRequestBody.get("horaDoFim"));

        Assertions.assertEquals("Consulta enviada com sucesso!", response);
    }
}
