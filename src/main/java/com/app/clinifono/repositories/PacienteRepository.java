package com.app.clinifono.repositories;

import com.app.clinifono.entities.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<PacienteEntity,Long> {
}
