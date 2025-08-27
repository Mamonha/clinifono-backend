package com.app.clinifono.repositories;

import com.app.clinifono.entities.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;

public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {
    UserDetails findByEmail(String email);
    
    @Query(value = """
        SELECT 
            'usuarios' as tabela,
            id,
            nome,
            email,
            telefone,
            created_at as data_criacao,
            updated_at as data_modificacao,
            NULL as criado_por,
            NULL as modificado_por
        FROM usuarios
        UNION ALL
        SELECT 
            'consultas' as tabela,
            id,
            descricao as nome,
            NULL as email,
            NULL as telefone,
            created_at as data_criacao,
            updated_at as data_modificacao,
            created_by as criado_por,
            modified_by as modificado_por
        FROM consultas
        UNION ALL
        SELECT 
            'pacientes' as tabela,
            id,
            nome,
            cpf as email,
            telefone,
            created_at as data_criacao,
            updated_at as data_modificacao,
            created_by as criado_por,
            modified_by as modificado_por
        FROM pacientes
        UNION ALL
        SELECT 
            'enderecos' as tabela,
            id,
            CONCAT(nome_rua, ', ', numero_da_casa, ' - ', bairro, ', ', cidade, '/', estado) as nome,
            cep as email,
            NULL as telefone,
            created_at as data_criacao,
            updated_at as data_modificacao,
            created_by as criado_por,
            modified_by as modificado_por
        FROM enderecos
        ORDER BY data_criacao DESC
        """, nativeQuery = true)
    List<Object[]> findAllAuditData();
}
