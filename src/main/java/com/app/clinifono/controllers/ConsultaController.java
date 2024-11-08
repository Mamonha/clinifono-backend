package com.app.clinifono.controllers;

import com.app.clinifono.dto.consulta.*;
import com.app.clinifono.mapper.ConsultaMapper;
import com.app.clinifono.services.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/consulta")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private ConsultaMapper consultaMapper;

    @GetMapping("/totalpormes")
    public List<Integer> obterTotalConsultasPorMes() {
        return consultaService.contarConsultasPorMes();
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseConsultaDto> create(@RequestBody ConsultaDto dto){
        var consulta = consultaService.save(consultaMapper.toEntity(dto));
        consultaService.sendConsultaToExternalService(consulta);
        return new ResponseEntity<>(consultaMapper.toDto(consulta), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseConsultaDto> update( @PathVariable Long id, @Valid @RequestBody ConsultaUpdateDto dto ){
        var consulta = consultaService.update(consultaMapper.toUpdateEntity(dto), id);
        return new ResponseEntity<>(consultaMapper.toDto(consulta), HttpStatus.OK);
    }

    @PutMapping("/confirmar/{id}")
    public ResponseEntity<ResponseConsultaDto> confirmar(@PathVariable Long id, @Valid @RequestBody ConsultaConfirmDto dto ){
        var consulta = consultaService.confirmarConsulta(consultaMapper.toConfirm(dto), id);
        return new ResponseEntity<>(consultaMapper.toDto(consulta), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        consultaService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<ResponseConsultaDto> findById(@PathVariable Long id){
        var consulta = consultaService.findById(id);
        return new ResponseEntity<>(consultaMapper.toDto(consulta),HttpStatus.OK);
    }

    @GetMapping("/findall")
    public ResponseEntity<List<ResponseConsultaDto>> findall(){
        var consultas = consultaService.findAll();
        return new ResponseEntity<>(consultas.stream().map(consultaMapper::toDto).collect(Collectors.toList())
                ,HttpStatus.OK);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ConsultaDashboardDto> getDashboard() {
        ConsultaDashboardDto dashboardData = consultaService.getDashboardData();
        return new ResponseEntity<>(dashboardData, HttpStatus.OK);
    }
}
