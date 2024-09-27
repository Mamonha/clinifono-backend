package com.app.clinifono.services;

import com.app.clinifono.configuration.exceptions.EntityNotFoundException;
import com.app.clinifono.configuration.exceptions.UniqueValueException;
import com.app.clinifono.entities.Endereco;
import com.app.clinifono.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;


    @Transactional
    public Endereco save(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    @Transactional(readOnly = true)
    public Endereco findById(Long id) {
        return enderecoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Endereco não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Endereco> findAll() {
        return enderecoRepository.findAll();
    }

    @Transactional
    public Endereco update(Long id, Endereco enderecoAtualizado) {

        var enderecoExistente = findById(id);

        if (enderecoAtualizado.getNomeRua() != null && !enderecoAtualizado.getNomeRua().isBlank()) {
            enderecoExistente.setNomeRua(enderecoAtualizado.getNomeRua());
        }
        if (enderecoAtualizado.getCep() != null && !enderecoAtualizado.getCep().isBlank()) {
            enderecoExistente.setCep(enderecoAtualizado.getCep());
        }
        if (enderecoAtualizado.getBairro() != null && !enderecoAtualizado.getBairro().isBlank()) {
            enderecoExistente.setBairro(enderecoAtualizado.getBairro());
        }
        if (enderecoAtualizado.getEstado() != null && !enderecoAtualizado.getEstado().isBlank()) {
            enderecoExistente.setEstado(enderecoAtualizado.getEstado());
        }
        if (enderecoAtualizado.getCidade() != null && !enderecoAtualizado.getCidade().isBlank()) {
            enderecoExistente.setCidade(enderecoAtualizado.getCidade());
        }
        if (enderecoAtualizado.getNumeroDaCasa() != null && !enderecoAtualizado.getNumeroDaCasa().isBlank()) {
            enderecoExistente.setNumeroDaCasa(enderecoAtualizado.getNumeroDaCasa());
        }

        try {
            return enderecoRepository.save(enderecoExistente);
        } catch (DataIntegrityViolationException ex) {
            throw new UniqueValueException("Erro ao atualizar o endereço. Verifique se algum campo está duplicado.");
        }
    }
}
