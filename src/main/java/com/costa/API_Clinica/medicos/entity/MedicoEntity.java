package com.costa.API_Clinica.medicos.entity;

import com.costa.API_Clinica.consulta.entity.ConsultaEntity;
import com.costa.API_Clinica.especialidade.entity.EspcialidadeEntity;
import com.costa.API_Clinica.pacientes.entity.Ativo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;
    private String crm;

    @Enumerated(EnumType.STRING)
    private Ativo estado;
    private String senha;
    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;


    @ManyToOne
    @JoinColumn(name = "epspecialidade_id", referencedColumnName = "id")
    private EspcialidadeEntity especialidade;

    @OneToMany(mappedBy = "medico")
    private Set<ConsultaEntity> consultas = new HashSet<>();
}
