package com.app.clinifono.repositories;

import com.app.clinifono.entities.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {
    UserDetails findByEmail(String email);
}
