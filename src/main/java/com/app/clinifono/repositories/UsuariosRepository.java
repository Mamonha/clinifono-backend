package com.app.clinifono.repositories;

import com.app.clinifono.entities.UsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosRepository extends JpaRepository<UsuariosEntity, Long> {
}
