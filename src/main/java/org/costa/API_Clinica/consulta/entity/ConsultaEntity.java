package org.costa.API_Clinica.consulta.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.costa.API_Clinica.medicos.entity.MedicoEntity;
import org.costa.API_Clinica.pacientes.entity.PacienteEntity;
import org.costa.API_Clinica.pagamento.entity.PagamentoEntity;
import org.costa.API_Clinica.prontuario.entity.PronturaioEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "consultas")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;


    @ManyToOne
    @JoinColumn(name = "medico_id", referencedColumnName = "id")
    @JsonManagedReference
    private MedicoEntity medico;


    @ManyToOne
    @JoinColumn(name = "paciente_id", referencedColumnName = "id")
    @JsonManagedReference
    private PacienteEntity paciente;


    @OneToOne(mappedBy = "consulta")
    private PronturaioEntity prontuario;

    @OneToOne(mappedBy = "consulta")
    private PagamentoEntity pagamento;

    private String dataConsulta;
    private String horaConsulta;

    @Enumerated(EnumType.STRING)
    private ConsultaStatus consultaStatus;

    private String motivoCancelamento;

    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;


    public String[] returnProntuario(){
        var prontuario = this.getProntuario();

        if(prontuario == null){
            String[] dados = {"nenhum prontuario elaborado"};
            return dados;
        }

        if(prontuario.getDiagnostico() == null
                || prontuario.getPrescricao() == null
                || prontuario.getObservacao() == null ){
            String[] dados = {"nenhum prontuario elaborado"};
            return dados;
        }


        String[] dados = {prontuario.getDiagnostico(),
                prontuario.getPrescricao(),
                prontuario.getObservacao()};

        return dados;
    }

}
