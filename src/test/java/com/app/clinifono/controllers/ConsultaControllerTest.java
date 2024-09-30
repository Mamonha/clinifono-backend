package com.app.clinifono.controllers;

import com.app.clinifono.dto.consulta.ConsultaConfirmDto;
import com.app.clinifono.dto.consulta.ConsultaDto;
import com.app.clinifono.dto.consulta.ConsultaUpdateDto;
import com.app.clinifono.dto.consulta.ResponseConsultaDto;
import com.app.clinifono.entities.Consulta;
import com.app.clinifono.entities.Status;
import com.app.clinifono.mapper.ConsultaMapper;
import com.app.clinifono.services.ConsultaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsultaControllerTest {

    @Mock
    private ConsultaService consultaService;

    @Mock
    private ConsultaMapper consultaMapper;

    @InjectMocks
    private ConsultaController consultaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateConsulta() {
        ConsultaDto consultaDto = new ConsultaDto(LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0), "Descrição Teste", null, null);
        Consulta consultaEntity = new Consulta();
        ResponseConsultaDto responseDto = new ResponseConsultaDto(1L, null, LocalTime.of(9, 0), LocalTime.of(10, 0), "Descrição Teste", Status.PENDING, null, null);
        when(consultaMapper.toEntity(consultaDto)).thenReturn(consultaEntity);
        when(consultaService.save(consultaEntity)).thenReturn(consultaEntity);
        when(consultaMapper.toDto(consultaEntity)).thenReturn(responseDto);
        ResponseEntity<ResponseConsultaDto> response = consultaController.create(consultaDto);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
        verify(consultaService).sendConsultaToExternalService(consultaEntity);
    }

    @Test
    void testUpdateConsulta() {
        ConsultaUpdateDto updateDto = new ConsultaUpdateDto(LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0), "Descrição Atualizada");
        Consulta consultaEntity = new Consulta();
        ResponseConsultaDto responseDto = new ResponseConsultaDto(1L, null, LocalTime.of(9, 0), LocalTime.of(10, 0), "Descrição Atualizada", Status.PENDING, null, null);
        when(consultaMapper.toUpdateEntity(updateDto)).thenReturn(consultaEntity);
        when(consultaService.update(consultaEntity, 1L)).thenReturn(consultaEntity);
        when(consultaMapper.toDto(consultaEntity)).thenReturn(responseDto);
        ResponseEntity<ResponseConsultaDto> response = consultaController.update(1L, updateDto);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testConfirmarConsulta() {
        ConsultaConfirmDto confirmDto = new ConsultaConfirmDto(Status.CONFIRMED);
        Consulta consultaEntity = new Consulta();
        ResponseConsultaDto responseDto = new ResponseConsultaDto(1L, null, LocalTime.of(9, 0), LocalTime.of(10, 0), "Descrição", Status.CONFIRMED, null, null);
        when(consultaMapper.toConfirm(confirmDto)).thenReturn(consultaEntity);
        when(consultaService.confirmarConsulta(consultaEntity, 1L)).thenReturn(consultaEntity);
        when(consultaMapper.toDto(consultaEntity)).thenReturn(responseDto);
        ResponseEntity<ResponseConsultaDto> response = consultaController.confirmar(1L, confirmDto);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testDeleteConsulta() {
        doNothing().when(consultaService).delete(1L);
        ResponseEntity<Void> response = consultaController.delete(1L);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(consultaService, times(1)).delete(1L);
    }

    @Test
    void testFindById() {
        Consulta consultaEntity = new Consulta();
        ResponseConsultaDto responseDto = new ResponseConsultaDto(1L, null, LocalTime.of(9, 0), LocalTime.of(10, 0), "Descrição Teste", Status.PENDING, null, null);
        when(consultaService.findById(1L)).thenReturn(consultaEntity);
        when(consultaMapper.toDto(consultaEntity)).thenReturn(responseDto);
        ResponseEntity<ResponseConsultaDto> response = consultaController.findById(1L);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testFindAllConsultas() {
        List<Consulta> consultas = List.of(new Consulta(), new Consulta());
        List<ResponseConsultaDto> responseDtos = List.of(
                new ResponseConsultaDto(1L, null, LocalTime.of(9, 0), LocalTime.of(10, 0), "Descrição 1", Status.PENDING, null, null),
                new ResponseConsultaDto(2L, null, LocalTime.of(11, 0), LocalTime.of(12, 0), "Descrição 2", Status.CONFIRMED, null, null)
        );
        when(consultaService.findAll()).thenReturn(consultas);
        when(consultaMapper.toDto(any(Consulta.class))).thenReturn(responseDtos.get(0), responseDtos.get(1));
        ResponseEntity<List<ResponseConsultaDto>> response = consultaController.findall();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }
}
