package com.app.clinifono.controllers;

import com.app.clinifono.dto.endereco.EnderecoUpdateDto;
import com.app.clinifono.dto.endereco.ResponseEnderecoDto;
import com.app.clinifono.entities.Endereco;
import com.app.clinifono.mapper.EnderecoMapper;
import com.app.clinifono.services.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private EnderecoMapper enderecoMapper;

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseEnderecoDto> update(@PathVariable Long id, @RequestBody @Valid EnderecoUpdateDto dto) {
        Endereco endereco = enderecoService.update(id, enderecoMapper.toUpdateEntity(dto));
        return new ResponseEntity<>(enderecoMapper.toDto(endereco), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseEnderecoDto> create(@RequestBody @Valid EnderecoUpdateDto dto) {
        Endereco endereco = enderecoService.save(enderecoMapper.toUpdateEntity(dto));
        return new ResponseEntity<>(enderecoMapper.toDto(endereco), HttpStatus.CREATED);
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<ResponseEnderecoDto> findById(@PathVariable Long id) {
        Endereco endereco = enderecoService.findById(id);
        return new ResponseEntity<>(enderecoMapper.toDto(endereco), HttpStatus.OK);
    }

    @GetMapping("/findall")
    public ResponseEntity<List<ResponseEnderecoDto>> findAll() {
        List<Endereco> enderecos = enderecoService.findAll();
        List<ResponseEnderecoDto> responseDtos = enderecos.stream().map(enderecoMapper::toDto).toList();
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }
}
