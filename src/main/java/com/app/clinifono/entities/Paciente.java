package com.app.clinifono.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pacientes")
@EntityListeners(AuditingEntityListener.class)
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(unique = true)
    private String cpf;
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    private String telefone;
    private LocalDate dataCadastro = LocalDate.now();

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

    @OneToOne(mappedBy = "paciente", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Endereco enderecos;

    @JsonManagedReference
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    private List<Consulta> consultas;

}
