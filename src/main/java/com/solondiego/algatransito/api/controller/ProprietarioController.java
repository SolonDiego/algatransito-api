package com.solondiego.algatransito.api.controller;

import com.solondiego.algatransito.domain.model.Proprietario;
import com.solondiego.algatransito.domain.repository.ProprietarioRepository;
import com.solondiego.algatransito.domain.service.RegistroProprietarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/proprietarios")
public class ProprietarioController {
//    @Autowired
//    private ProprietarioRepository repository; //pratica antiga

    private final RegistroProprietarioService registroProprietarioService;

    private final ProprietarioRepository repository;

//    public ProprietarioController(ProprietarioRepository repository){
//        this.repository = repository;
//    } esse é sem usar a anotação AllArgsConstructor

    @GetMapping
    public List<Proprietario> listar() {
        return repository.findAll();
    }

    @GetMapping("/{proprietarioId}")
    public ResponseEntity<Proprietario> buscar(@PathVariable Long proprietarioId) {

        return repository.findById(proprietarioId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

//        Optional<Proprietario> proprietario = repository.findById(proprietarioId);
//        if (proprietario.isPresent()) {
//            return ResponseEntity.ok(proprietario.get());
//        }
//        return ResponseEntity.notFound().build();
    }

    @GetMapping("/nome")
    public List<Proprietario> buscarPeloNome() {
        return repository.findByNomeContaining("Joao");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Proprietario adicionar(@Valid @RequestBody Proprietario proprietario) {
        return registroProprietarioService.salvar(proprietario);
    }

    @PutMapping("/{proprietarioId}")
    public ResponseEntity<Proprietario> atualizar(@PathVariable Long proprietarioId, @Valid @RequestBody Proprietario proprietario) {
        if (!repository.existsById(proprietarioId)) {
            return ResponseEntity.notFound().build();
        }
        proprietario.setId(proprietarioId);
        Proprietario proprietarioAtualizado = registroProprietarioService.salvar(proprietario);
        return ResponseEntity.ok(proprietarioAtualizado);
    }

    @DeleteMapping("/{proprietarioId}")
    public ResponseEntity<Void> remover(@PathVariable Long proprietarioId) {
        if (!repository.existsById(proprietarioId)) {
            return ResponseEntity.notFound().build();
        }
        registroProprietarioService.excluir(proprietarioId);
        return ResponseEntity.noContent().build();
    }
}
