package org.costa.API_Clinica.consulta.dto.response;

import jakarta.persistence.*;
import org.costa.API_Clinica.consulta.entity.ConsultaStatus;
import org.costa.API_Clinica.medicos.entity.MedicoEntity;
import org.costa.API_Clinica.pacientes.entity.PacienteEntity;
import org.costa.API_Clinica.pagamento.entity.PagamentoEntity;
import org.costa.API_Clinica.prontuario.entity.PronturaioEntity;

import java.time.LocalDate;
import java.util.UUID;

public record ConsultasResponseDto(
        UUID id,
        UUID medico,
        UUID paciente,
        String data_consulta,
        String hora_consulta,
        PronturaioEntity prontuario,
        ConsultaStatus consultaStatus,
        PagamentoEntity pagamento,
        String motivoCancelamento,
        LocalDate DataCriacao,
        LocalDate DataAtualizacao
) {
}
