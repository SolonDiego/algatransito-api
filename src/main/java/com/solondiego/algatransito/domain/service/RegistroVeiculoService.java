package com.solondiego.algatransito.domain.service;

import com.solondiego.algatransito.domain.exception.NegocioException;
import com.solondiego.algatransito.domain.model.Proprietario;
import com.solondiego.algatransito.domain.model.StatusVeiculo;
import com.solondiego.algatransito.domain.model.Veiculo;
import com.solondiego.algatransito.domain.repository.VeiculoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;


@AllArgsConstructor
@Service
public class RegistroVeiculoService {

    private VeiculoRepository veiculoRepository;
    private RegistroProprietarioService registroProprietarioService;

    public Veiculo buscar(Long veiculoId){

        return veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new NegocioException("Veiculo não encontrado."));
    }

    @Transactional
    public Veiculo cadastrar(Veiculo novoVeiculo) {

        if (novoVeiculo.getId() != null) {
            throw new NegocioException("Veículo a ser cadastrado não deve possuir um código.");
        }

        boolean placaEmUso = veiculoRepository.findByPlaca(novoVeiculo.getPlaca())
                .filter(veiculo -> !veiculo.equals(novoVeiculo))
                .isPresent();

        if (placaEmUso) {
            throw new NegocioException("Já existe um veículo cadastrado com essa placa.");
        }

        Proprietario proprietario = registroProprietarioService.buscar(novoVeiculo.getProprietario().getId());

        novoVeiculo.setProprietario(proprietario);
        novoVeiculo.setStatus(StatusVeiculo.REGULAR);
        novoVeiculo.setDataCadastro(OffsetDateTime.now());

        return veiculoRepository.save(novoVeiculo);
    }

}
