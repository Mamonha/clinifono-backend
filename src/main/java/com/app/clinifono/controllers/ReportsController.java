package com.app.clinifono.controllers;

import com.app.clinifono.dto.usuario.AuditReportDto;
import com.app.clinifono.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportsController {

    @Autowired
    private UsuarioService usuarioService;

   
    @GetMapping("/audit")
    public ResponseEntity<List<AuditReportDto>> getAuditReport() {
        List<AuditReportDto> auditData = usuarioService.getAuditReport();
        return ResponseEntity.ok(auditData);
    }
} 