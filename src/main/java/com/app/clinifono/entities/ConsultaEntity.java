package com.app.clinifono.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "consultas")
public class ConsultaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataAgendamento;
    private String descricao;
    private boolean isConfirmed;

    @ManyToOne
    private UsuariosEntity usuario;

    @ManyToOne
    private PacienteEntity paciente;
}