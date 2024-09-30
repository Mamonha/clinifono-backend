package com.app.clinifono.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "enderecos")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeRua;
    private String cep;
    private String bairro;
    private String estado;
    private String cidade;
    private String numeroDaCasa;

    @OneToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
}
