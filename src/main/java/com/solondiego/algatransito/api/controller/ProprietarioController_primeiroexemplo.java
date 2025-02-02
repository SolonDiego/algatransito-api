package com.solondiego.algatransito.api.controller;

import com.solondiego.algatransito.domain.model.Proprietario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProprietarioController_primeiroexemplo {

    @PersistenceContext //persistir direto sem precisar do repositorio
    private EntityManager manager;

    @GetMapping("/proprietariosEX")
    public List<Proprietario> listar() {

        return manager.createQuery("from Proprietario", Proprietario.class).getResultList(); //mesma coisa do codigo de baixo

//        TypedQuery<Proprietario> query = manager
//                .createQuery("from Proprietario", Proprietario.class); // retorna o objeto proprietario
//
//        return query.getResultList();
    }

}
