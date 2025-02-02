package com.solondiego.algatransito.api.controller;

import com.solondiego.algatransito.api.assembler.VeiculoAssembler;
import com.solondiego.algatransito.api.model.VeiculoModel;
import com.solondiego.algatransito.api.model.input.VeiculoInput;
import com.solondiego.algatransito.domain.model.Veiculo;
import com.solondiego.algatransito.domain.repository.VeiculoRepository;
import com.solondiego.algatransito.domain.service.RegistroVeiculoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final VeiculoRepository veiculoRepository;
    private final RegistroVeiculoService registroVeiculoService;
    private final VeiculoAssembler veiculoAssembler;

    @GetMapping
    public List<VeiculoModel> listar() {
        return veiculoAssembler.toCollectionModel(veiculoRepository.findAll());
    }

//    @GetMapping("/{veiculoId}")
//    public ResponseEntity<VeiculoModel> buscar(@PathVariable Long veiculoId) {
//        return veiculoRepository.findById(veiculoId)
//                .map(veiculo -> {
//                    var veiculoModel = new VeiculoModel();
//                    veiculoModel.setId(veiculo.getId());
//                    veiculoModel.setNomeProprietario(veiculo.getProprietario().getNome());
//                    veiculoModel.setMarca(veiculo.getMarca());
//                    veiculoModel.setModelo(veiculo.getModelo());
//                    veiculoModel.setPlaca(veiculo.getPlaca());
//                    veiculoModel.setStatus(veiculo.getStatus());
//                    veiculoModel.setDataCadastro(veiculo.getDataCadastro());
//                    veiculoModel.setDataApreensao(veiculo.getDataApreensao());
//                    return veiculoModel;
//                })
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }


    @GetMapping("/{veiculoId}")
    public ResponseEntity<VeiculoModel> buscar(@PathVariable Long veiculoId) {
        return veiculoRepository.findById(veiculoId)
                .map(veiculoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public Veiculo cadastrar(@Valid @RequestBody Veiculo veiculo) {
//        return registroVeiculoService.cadastrar(veiculo);
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VeiculoModel cadastrar(@Valid @RequestBody VeiculoInput veiculoInput) {
        Veiculo novoVeiculo = veiculoAssembler.toEntity(veiculoInput);
        Veiculo veiculoCadastrado = registroVeiculoService.cadastrar(novoVeiculo);

        return veiculoAssembler.toModel(veiculoCadastrado);

        //return veiculoAssembler.toModel(registroVeiculoService.cadastrar(veiculo));

    }
}
