package com.costa.API_Clinica.pacientes.entity;

import com.costa.API_Clinica.consulta.entity.ConsultaEntity;
import com.costa.API_Clinica.pagamento.entity.PagamentoEntity;
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
@AllArgsConstructor
@NoArgsConstructor
public class PacienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;
    private String cpf;
    private String email;

    @Enumerated(EnumType.STRING)
    private Ativo estado;

    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;
    private String senha;

    @OneToMany(mappedBy = "paciente")
    private HashSet<ConsultaEntity> consultasPaciente = new HashSet<>();

    @OneToMany(mappedBy = "paciente")
    private Set<PagamentoEntity> pagamento = new HashSet<>();

}
