package org.costa.API_Clinica.pagamento.entity;

import org.costa.API_Clinica.consulta.entity.ConsultaEntity;
import org.costa.API_Clinica.pacientes.entity.PacienteEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "pagamentos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "consulta_id", referencedColumnName = "id")
    private ConsultaEntity consulta;

    @ManyToOne
    @JoinColumn(name = "paciente_id", referencedColumnName = "id")
    private PacienteEntity paciente;

    @Enumerated(EnumType.STRING)
    private FormaPagamentoEnum formaPagamento;

    @Enumerated(EnumType.STRING)
    private StatusPagamentoEnum status;

    private BigDecimal valor;
    private String DataVencimento;
    private String DataPagamento;
    private LocalDate dataCriado;


}
