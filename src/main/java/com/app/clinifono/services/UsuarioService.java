package com.app.clinifono.services;

import com.app.clinifono.configuration.exceptions.EntityNotFoundException;
import com.app.clinifono.configuration.exceptions.PasswordMissmatchException;
import com.app.clinifono.configuration.exceptions.UnauthorizedException;
import com.app.clinifono.configuration.exceptions.UniqueValueException;
import com.app.clinifono.entities.Usuarios;
import com.app.clinifono.repositories.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Usuarios create(Usuarios usuariosEntity){
        try {
            String hashedPassword = passwordEncoder.encode(usuariosEntity.getSenha());
            usuariosEntity.setSenha(hashedPassword);
            return this.usuariosRepository.save(usuariosEntity);
        } catch (DataIntegrityViolationException ex){
            throw new UniqueValueException("Email ou Numero de telefone já cadastrados");
        }
    }

    @Transactional
    public Usuarios update(Usuarios usuariosEntity, Long id){
        try {
            var user = findById(id);

            if (!usuariosEntity.getNome().equals(user.getNome()) && !usuariosEntity.getNome().isBlank()){
                user.setNome(usuariosEntity.getNome());
            }
            if (!usuariosEntity.getEmail().equals(user.getEmail()) && !usuariosEntity.getEmail().isBlank()){
                user.setEmail(usuariosEntity.getEmail());
            }
            if (!usuariosEntity.getTelefone().equals(user.getTelefone()) && !usuariosEntity.getTelefone().isBlank()){
                user.setTelefone(usuariosEntity.getTelefone());
            }

            return usuariosRepository.save(user);
        } catch (DataIntegrityViolationException ex){
            throw new UniqueValueException("Email ou Numero de telefone já cadastrados");
        }
    }

    @Transactional
    public String delete(long id){
        this.usuariosRepository.deleteById(id);
        return "Deletado";
    }

    @Transactional(readOnly = true)
    public Usuarios findById(long id){
        Usuarios usuario = this.usuariosRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        return usuario;
    }

    @Transactional(readOnly = true)
    public List<Usuarios> findALl(){
        List<Usuarios> list = this.usuariosRepository.findAll();
        return list;
    }

    @Transactional
    public void atualizarSenha( Long id,String senhaAtual, String novaSenha){
        var user = findById(id);

        if (!user.getSenha().equals(senhaAtual)){
            throw new PasswordMissmatchException("A senha atual está incorreta");
        }
        user.setSenha(novaSenha);
        usuariosRepository.save(user);
    }

}

