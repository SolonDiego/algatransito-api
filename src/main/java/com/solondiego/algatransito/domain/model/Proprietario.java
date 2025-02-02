package com.solondiego.algatransito.domain.model;

import com.solondiego.algatransito.domain.validation.ValidationGroups;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
//@Table(name = "tb_proprietario") para alterar o nome da tabela
public class Proprietario {

    @NotNull(groups = ValidationGroups.ProprietarioID.class)
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column //se não tiver parametro não tem necessidade
    @NotBlank
    @Size(max = 60)
    private String nome;

    @NotBlank
    @Size(max = 255)
    @Email
    private String email;

    @NotBlank
    @Size(max = 20)
    //@Column(name = "fone") // caso na banco a coluna esteja com outro nome, e não queira mudar no codigo java
    private String telefone;

}
