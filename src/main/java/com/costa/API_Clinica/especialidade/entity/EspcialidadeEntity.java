package com.costa.API_Clinica.especialidade.entity;


import com.costa.API_Clinica.medicos.entity.MedicoEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "especialidade")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EspcialidadeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;

    private String nome;

    @OneToMany(mappedBy = "especialidade")
    private Set<MedicoEntity> medicos = new HashSet<>();

}
