package com.app.clinifono.controllers;

import com.app.clinifono.dto.paciente.PacienteDto;
import com.app.clinifono.dto.paciente.PacienteUpdateDto;
import com.app.clinifono.dto.paciente.ResponsePacienteDto;
import com.app.clinifono.entities.Paciente;
import com.app.clinifono.mapper.PacienteMapper;
import com.app.clinifono.services.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paciente")
@CrossOrigin("*")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private PacienteMapper pacienteMapper;

    @PostMapping("/create")
    public ResponseEntity<ResponsePacienteDto> create(@RequestBody @Valid PacienteDto dto){
        var paciente = pacienteService.save(pacienteMapper.toEntity(dto));
        return new ResponseEntity<>( pacienteMapper.toDto(paciente), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponsePacienteDto> update( @PathVariable Long id, @Valid @RequestBody PacienteUpdateDto dto ){
        var paciente = pacienteService.update(pacienteMapper.toUpdateEntity(dto), id);
        return new ResponseEntity<>(pacienteMapper.toDto(paciente), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pacienteService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<ResponsePacienteDto> findById(@PathVariable Long id){
        return new ResponseEntity<>(pacienteMapper.toDto(pacienteService.findById(id)),HttpStatus.OK);
    }

    @GetMapping("/findall")
    public ResponseEntity<List<ResponsePacienteDto>> findall(){
        List<Paciente> pacientes = pacienteService.findAll();
        return new ResponseEntity<>(pacientes.stream().map(pacienteMapper::toDto).toList(),HttpStatus.OK);
    }


}
