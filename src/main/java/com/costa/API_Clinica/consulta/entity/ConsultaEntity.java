package com.costa.API_Clinica.consulta.entity;

import com.costa.API_Clinica.medico.entity.MedicoEntity;
import com.costa.API_Clinica.paciente.entity.PacienteEntity;
import com.costa.API_Clinica.prontuario.entity.PronturaioEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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
