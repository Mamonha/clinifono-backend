package com.app.clinifono.services;

import com.app.clinifono.entities.UsuariosEntity;
import com.app.clinifono.repositories.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    private UsuariosEntity create(UsuariosEntity usuariosEntity){
        return this.usuariosRepository.save(usuariosEntity);
    }

    private UsuariosEntity update(UsuariosEntity usuariosEntity, long id){
        usuariosEntity.setId(id);
        return this.usuariosRepository.save(usuariosEntity);
    }

    private String delete(long id){
        this.usuariosRepository.deleteById(id);
        return "Deletado";
    }

    private UsuariosEntity findById(long id){
        UsuariosEntity usuario = this.usuariosRepository.findById(id).get();
        return usuario;
    }

    private List<UsuariosEntity> findALl(){
        List<UsuariosEntity> list = this.usuariosRepository.findAll();
        return list;
    }
}

