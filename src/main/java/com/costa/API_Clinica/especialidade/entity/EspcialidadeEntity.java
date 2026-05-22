package com.costa.API_Clinica.especialidade.entity;


import com.costa.API_Clinica.medicos.entity.MedicoEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EspcialidadeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    @OneToMany(mappedBy = "especialidade")
    private Set<MedicoEntity> medicos = new HashSet<>();

}
