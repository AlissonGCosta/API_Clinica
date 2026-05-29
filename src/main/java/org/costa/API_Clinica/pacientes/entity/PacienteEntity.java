package org.costa.API_Clinica.pacientes.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.costa.API_Clinica.consulta.entity.ConsultaEntity;
import org.costa.API_Clinica.pagamento.dto.response.PagamentoResponse;
import org.costa.API_Clinica.pagamento.entity.PagamentoEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "pacientes")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PacienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, nullable = false, updatable = false)
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
    @JsonBackReference
    private Set<ConsultaEntity> consultasPaciente = new HashSet<>();

    @OneToMany(mappedBy = "paciente")
    private Set<PagamentoEntity> pagamento = new HashSet<>();


}
