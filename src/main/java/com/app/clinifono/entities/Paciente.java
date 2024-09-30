package com.app.clinifono.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(unique = true)
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;


    @OneToOne(mappedBy = "paciente", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Endereco enderecos;

    @OneToMany(mappedBy = "paciente")
    private List<Consulta> consultas;

}
