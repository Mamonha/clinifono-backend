package com.app.clinifono.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "enderecos")
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String modifiedBy;
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
}
