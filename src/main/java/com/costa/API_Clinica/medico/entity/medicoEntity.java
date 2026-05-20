package com.costa.API_Clinica.medico.entity;

import com.costa.API_Clinica.paciente.entity.Ativo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class medicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;
    private String crm;
    private Ativo estado;
    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;
}
