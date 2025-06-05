package com.app.clinifono.controllers;


import com.app.clinifono.dto.usuario.LoginDto;

import com.app.clinifono.services.AutenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AutenticationController {

    @Autowired
    private AutenticationService loginService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDto dto) {
        var tk = loginService.login(dto);
        return tk;
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestParam("refresh_token") String refreshToken){
        return loginService.refreshToken(refreshToken);
    }

}
