package org.costa.API_Clinica.consulta.entity;

import org.costa.API_Clinica.medicos.entity.MedicoEntity;
import org.costa.API_Clinica.pacientes.entity.PacienteEntity;
import org.costa.API_Clinica.prontuario.entity.PronturaioEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    private MedicoEntity medico;


    @ManyToOne
    @JoinColumn(name = "paciente_id", referencedColumnName = "id")
    private PacienteEntity paciente;


    @OneToOne(mappedBy = "consulta")
    private PronturaioEntity prontuario;


}
