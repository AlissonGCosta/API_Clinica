package org.costa.API_Clinica.medicos.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.costa.API_Clinica.consulta.entity.ConsultaEntity;
import org.costa.API_Clinica.especialidade.entity.EspcialidadeEntity;
import org.costa.API_Clinica.pacientes.entity.Ativo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "medicos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;

    private String nome;
    private String crm;

    @Enumerated(EnumType.STRING)
    private Ativo estado;
    private String senha;
    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;


    @ManyToOne
    @JoinColumn(name = "especialidade_id", referencedColumnName = "id")
    @JsonManagedReference
    private EspcialidadeEntity especialidade;

    @OneToMany(mappedBy = "medico")
    @JsonBackReference
    private Set<ConsultaEntity> consultas = new HashSet<>();
}
