package com.app.clinifono.controllers;

import com.app.clinifono.entities.Consulta;
import com.app.clinifono.entities.Paciente;
import com.app.clinifono.services.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consulta")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @PostMapping("/create")
    public ResponseEntity<Consulta> create(@RequestBody Consulta consulta){
        return new ResponseEntity<>(consultaService.save(consulta), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Consulta> update( @PathVariable Long id, @Valid @RequestBody Consulta consulta ){
        return new ResponseEntity<>(consultaService.update(consulta,id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        consultaService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<Consulta> findById(@PathVariable Long id){
        return new ResponseEntity<>(consultaService.findById(id),HttpStatus.OK);
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Consulta>> findall(){
        return new ResponseEntity<>(consultaService.findAll(),HttpStatus.OK);
    }

}
