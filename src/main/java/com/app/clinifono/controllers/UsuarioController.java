package com.app.clinifono.controllers;

import com.app.clinifono.dto.usuario.*;
import com.app.clinifono.mapper.UserMapper;
import com.app.clinifono.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UserMapper userMapper;
    @PreAuthorize("hasAuthority('MAMONHA')")
    @PostMapping("/save")
    public ResponseEntity<ResponseUsuarioDto> save(@RequestBody @Valid UsuarioDto dto){
        var obj = usuarioService.create(userMapper.toEntity(dto));
        return new ResponseEntity<>(userMapper.toDto(obj), HttpStatus.CREATED);
    }
    @PreAuthorize("hasAuthority('MAMONHA')")
    @PutMapping("/senha/{id}")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UsuarioSenhaUpdateDto dto, @PathVariable Long id){
        usuarioService.atualizarSenha(id, dto.senhaAtual(), dto.novaSenha());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PreAuthorize("hasAuthority('MAMONHA')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseUsuarioDto> update(@PathVariable Long id,@RequestBody @Valid UsuarioUpdateDto dto) {
        var user = usuarioService.update(userMapper.updateDto(dto), id);
        return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<ResponseUsuarioDto> findById(@PathVariable Long id) {
        var user = usuarioService.findById(id);
        return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('MAMONHA')")
    @GetMapping("/findall")
    public ResponseEntity<List<ResponseUsuarioDto>> findAll() {
        var usuarios = usuarioService.findALl();
        return new ResponseEntity<>(usuarios.stream().map(userMapper::toDto).collect(Collectors.toList()), HttpStatus.OK);
    }

}
