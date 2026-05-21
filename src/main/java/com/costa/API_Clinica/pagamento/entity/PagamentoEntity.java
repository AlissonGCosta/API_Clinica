package com.costa.API_Clinica.pagamento.entity;

import com.costa.API_Clinica.consulta.entity.ConsultaEntity;
import com.costa.API_Clinica.paciente.entity.PacienteEntity;
import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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
