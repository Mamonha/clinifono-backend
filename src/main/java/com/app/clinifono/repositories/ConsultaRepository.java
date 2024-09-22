package com.app.clinifono.repositories;

import com.app.clinifono.entities.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<Consulta,Long> {
}
