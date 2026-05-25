package org.costa.API_Clinica.prontuario.entity;

import org.costa.API_Clinica.consulta.entity.ConsultaEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "prontuario")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PronturaioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;

    private String diagnostico;
    private String prescricao;
    private String observacao;
    private LocalDate datacriacao;

    @OneToOne
    @JoinColumn(name = "consulta_id", referencedColumnName = "id")
    private ConsultaEntity consulta;
}
