package com.app.clinifono.controllers;

import com.app.clinifono.dto.ResponsePacienteDto;
import com.app.clinifono.entities.Paciente;
import com.app.clinifono.services.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paciente")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping("/create")
    public ResponseEntity<Paciente> create(@RequestBody Paciente paciente){
        return new ResponseEntity<>(pacienteService.save(paciente), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Paciente> update( @PathVariable Long id, @Valid @RequestBody Paciente paciente ){
        return new ResponseEntity<>(pacienteService.update(paciente,id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pacienteService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<Paciente> findById(@PathVariable Long id){
        return new ResponseEntity<>(pacienteService.findById(id),HttpStatus.OK);
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Paciente>> findall(){
        return new ResponseEntity<>(pacienteService.findAll(),HttpStatus.OK);
    }


}
