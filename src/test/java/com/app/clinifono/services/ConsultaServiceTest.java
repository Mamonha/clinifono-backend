package com.app.clinifono.services;

import com.app.clinifono.configuration.exceptions.BusinessException;
import com.app.clinifono.dto.consulta.ConsultaDashboardDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));
        when(consultaRepository.save(ArgumentMatchers.any(Consulta.class))).thenReturn(consulta);
        when(consultaRepository.findById(1L)).thenReturn(Optional.of(consulta));
        when(consultaRepository.findById(2L)).thenReturn(Optional.of(outraConsulta));
        when(consultaRepository.findAll()).thenReturn(List.of(consulta, outraConsulta));
    }

    @Test
    @DisplayName("Teste - Save")
    void cenario01() {
        var resultado = consultaService.save(consulta);
        assertEquals(consulta, resultado);
        assertEquals(consulta.getDescricao(), resultado.getDescricao());
    }

    @Test
    @DisplayName("Teste - Exception Save Data Inválida")
    void cenario02() {
        consulta.setDataAgendamento(LocalDate.of(2022, 9, 25)); // Data inválida no passado

        var exception = Assertions.assertThrows(BusinessException.class, () -> {
            consultaService.save(consulta);
        });

        assertEquals("A data é inválida", exception.getMessage());
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

        assertEquals("Hora de encerramento invalida", exception.getMessage());
    }

    @Test
    @DisplayName("Teste - Update")
    void cenario04() {
        consulta.setDescricao("Consulta atualizada");
        consulta.setDataAgendamento(LocalDate.now().plusDays(1)); // Data futura
        consulta.setHoraDeInicio(LocalTime.of(11, 0));
        consulta.setHoraDoFim(LocalTime.of(12, 0)); // Hora de encerramento válida

        var resultado = consultaService.update(consulta, 1L);

        assertEquals(1L, resultado.getId());
        assertEquals("Consulta atualizada", resultado.getDescricao());
    }

    @Test
    @DisplayName("Teste - Exception Update Data Inválida")
    void cenario05() {
        consulta.setDataAgendamento(LocalDate.of(2022, 9, 25)); // Data inválida

        var exception = Assertions.assertThrows(BusinessException.class, () -> {
            consultaService.update(consulta, 1L);
        });

        assertEquals("A data é inválida", exception.getMessage());
    }

    @Test
    @DisplayName("Teste - Confirmar Consulta")
    void cenario06() {
        consulta.setStatus(Status.CONFIRMED);

        var resultado = consultaService.confirmarConsulta(consulta, 1L);
        assertEquals(Status.CONFIRMED, resultado.getStatus());
    }

    @Test
    @DisplayName("Teste - Exception Confirmar Consulta")
    void cenario07() {
        consulta.setStatus(null);
        var exception = Assertions.assertThrows(BusinessException.class, () -> {
            consultaService.confirmarConsulta(consulta, 1L);
        });

        assertEquals("Erro ao confirmar a consulta devido a má formatação do status", exception.getMessage());
    }

    @Test
    @DisplayName("Teste - FindById")
    void cenario08() {
        var resultado = consultaService.findById(1L);
        assertEquals(consulta, resultado);
    }

    @Test
    @DisplayName("Teste - FindAll")
    void cenario09() {
        List<Consulta> consultas = consultaService.findAll();
        assertEquals(2, consultas.size());
        assertEquals("Consulta de rotina", consultas.get(0).getDescricao());
        assertEquals("Consulta de retorno", consultas.get(1).getDescricao());
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
        when(restTemplate.postForObject(
                Mockito.eq("http://localhost:8000/api/consultas"),
                ArgumentMatchers.any(Map.class),
                Mockito.eq(String.class))
        ).thenReturn(response);

        consultaService.sendConsultaToExternalService(consulta);

        ArgumentCaptor<Map<String, Object>> requestCaptor = ArgumentCaptor.forClass(Map.class);
        Mockito.verify(restTemplate, Mockito.times(1))
                .postForObject(Mockito.eq("http://localhost:8000/api/consultas"), requestCaptor.capture(), Mockito.eq(String.class));

        Map<String, Object> capturedRequestBody = requestCaptor.getValue();
        assertEquals(consulta.getId(), capturedRequestBody.get("consultaId"));
        assertEquals(paciente.getNome(), capturedRequestBody.get("pacienteNome"));
        assertEquals("11987654321", capturedRequestBody.get("telefone"));
        assertEquals(consulta.getDataAgendamento().toString(), capturedRequestBody.get("dataAgendamento"));
        assertEquals(consulta.getHoraDeInicio().toString(), capturedRequestBody.get("horaDeInicio"));
        assertEquals(consulta.getHoraDoFim().toString(), capturedRequestBody.get("horaDoFim"));

        assertEquals("Consulta enviada com sucesso!", response);
    }

    @Test
    void testGetDashboardData() {
        // Arrange
        long totalConsultas = 20L;
        long consultasConfirmadas = 10L;
        long consultasCanceladas = 5L;

        List<Map<String, Object>> consultasPorPacienteData = List.of(
                Map.of("paciente", "Paciente A", "totalConsultas", 7L),
                Map.of("paciente", "Paciente B", "totalConsultas", 3L)
        );

        List<Map<String, Object>> consultasPorMesData = List.of(
                Map.of("mes", 1, "totalConsultas", 5L),
                Map.of("mes", 2, "totalConsultas", 10L)
        );

        when(consultaRepository.count()).thenReturn(totalConsultas);
        when(consultaRepository.countByStatus(Status.CONFIRMED)).thenReturn(consultasConfirmadas);
        when(consultaRepository.countByStatus(Status.CANCELLED)).thenReturn(consultasCanceladas);
        when(consultaRepository.findConsultasPorPaciente()).thenReturn(consultasPorPacienteData);
        when(consultaRepository.findConsultasPorMes()).thenReturn(consultasPorMesData);

        ConsultaDashboardDto dashboardData = consultaService.getDashboardData();

        assertEquals(totalConsultas, dashboardData.totalConsultas());
        assertEquals(consultasConfirmadas, dashboardData.consultasConfirmadas());
        assertEquals(consultasCanceladas, dashboardData.consultasCanceladas());

        assertEquals(2, dashboardData.consultasPorPaciente().size());
        assertEquals("Paciente A", dashboardData.consultasPorPaciente().get(0).paciente());
        assertEquals(7L, dashboardData.consultasPorPaciente().get(0).totalConsultas());
        assertEquals("Paciente B", dashboardData.consultasPorPaciente().get(1).paciente());
        assertEquals(3L, dashboardData.consultasPorPaciente().get(1).totalConsultas());

        assertEquals(2, dashboardData.consultasPorMes().size());
        assertEquals(1, dashboardData.consultasPorMes().get(0).mes());
        assertEquals(5L, dashboardData.consultasPorMes().get(0).totalConsultas());
        assertEquals(2, dashboardData.consultasPorMes().get(1).mes());
        assertEquals(10L, dashboardData.consultasPorMes().get(1).totalConsultas());
    }
}
