package com.app.clinifono.controllers;

import com.app.clinifono.configuration.security.DadosTokenJwt;
import com.app.clinifono.configuration.security.TokenService;
import com.app.clinifono.dto.usuario.LoginDto;
import com.app.clinifono.entities.Usuarios;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@CrossOrigin("*")
public class AutenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<DadosTokenJwt> login(@RequestBody @Valid LoginDto dto) {
        var token = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
        var authentication = manager.authenticate(token);
        var tk = tokenService.tokenGenerate((Usuarios) authentication.getPrincipal());
        return new ResponseEntity<>(new DadosTokenJwt(tk),HttpStatus.OK);
    }
}
