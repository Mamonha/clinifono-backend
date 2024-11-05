package com.app.clinifono.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "consultas")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dataAgendamento;
    private LocalTime horaDeInicio;
    private LocalTime horaDoFim;
    private String descricao;
    private LocalDate dataConsulta = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JsonBackReference
    private Usuarios usuario;

    @ManyToOne
    @JsonBackReference
    private Paciente paciente;
}
