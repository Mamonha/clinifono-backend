package com.app.clinifono.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public String getAdmin() {
        return "ADMIN ACESSO";
    }

}
