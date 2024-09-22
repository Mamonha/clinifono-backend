package com.app.clinifono.services;

import com.app.clinifono.entities.Usuarios;
import com.app.clinifono.repositories.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    private Usuarios create(Usuarios usuariosEntity){
        return this.usuariosRepository.save(usuariosEntity);
    }

    private Usuarios update(Usuarios usuariosEntity, long id){
        usuariosEntity.setId(id);
        return this.usuariosRepository.save(usuariosEntity);
    }

    private String delete(long id){
        this.usuariosRepository.deleteById(id);
        return "Deletado";
    }

    private Usuarios findById(long id){
        Usuarios usuario = this.usuariosRepository.findById(id).get();
        return usuario;
    }

    private List<Usuarios> findALl(){
        List<Usuarios> list = this.usuariosRepository.findAll();
        return list;
    }
}

