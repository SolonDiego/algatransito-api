package com.solondiego.algatransito.domain.service;

import com.solondiego.algatransito.domain.exception.NegocioException;
import com.solondiego.algatransito.domain.model.Proprietario;
import com.solondiego.algatransito.domain.repository.ProprietarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class RegistroProprietarioService {

    private final ProprietarioRepository proprietarioRepository;

public Proprietario buscar(Long proprietarioId){
    return proprietarioRepository.findById(proprietarioId).orElseThrow(() -> new NegocioException("Proprietário não encontrado."));
}

    @Transactional
    public Proprietario salvar(Proprietario proprietario){
        boolean emailEmUso = proprietarioRepository.findByEmail(proprietario.getEmail()).filter(p -> !p.equals(proprietario)).isPresent();
        if(emailEmUso){
            throw new NegocioException("Ja existe um proprietario cadastrado com esse e-mail.");
        }
        return proprietarioRepository.save(proprietario);
    }

    @Transactional
    public void excluir(Long proprietarioId){
        proprietarioRepository.deleteById(proprietarioId);
    }

}
