package com.costa.API_Clinica.prontuario.entity;

import com.costa.API_Clinica.consulta.entity.ConsultaEntity;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PronturaioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String diagnostico;
    private String prescricao;
    private String observacao;
    private LocalDate datacriacao;

    @OneToOne
    @JoinColumn(name = "consulta_id", referencedColumnName = "id")
    private ConsultaEntity consulta;
}
