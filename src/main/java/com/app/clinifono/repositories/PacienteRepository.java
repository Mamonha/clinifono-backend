package com.app.clinifono.repositories;

import com.app.clinifono.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface PacienteRepository extends JpaRepository<Paciente,Long> {

    int countByDataCadastroBetween(LocalDate inicio, LocalDate fim);
}
