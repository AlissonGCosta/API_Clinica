package org.costa.API_Clinica.consulta.dto.response;

import jakarta.persistence.*;
import org.costa.API_Clinica.consulta.entity.ConsultaEntity;
import org.costa.API_Clinica.consulta.entity.ConsultaStatus;
import org.costa.API_Clinica.medicos.entity.MedicoEntity;
import org.costa.API_Clinica.pacientes.entity.PacienteEntity;
import org.costa.API_Clinica.pagamento.dto.response.PagamentoResponse;
import org.costa.API_Clinica.pagamento.entity.PagamentoEntity;
import org.costa.API_Clinica.pagamento.entity.StatusPagamentoEnum;
import org.costa.API_Clinica.prontuario.dto.response.ProntuarioResponseDto;
import org.costa.API_Clinica.prontuario.entity.PronturaioEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ConsultasResponseDto(
        UUID id,
        UUID medico,
        UUID paciente,
        String data_consulta,
        String hora_consulta,
        String[] prontuario,
        ConsultaStatus consultaStatus,
        StatusPagamentoEnum pagamento,
        String motivoCancelamento,
        LocalDate DataCriacao,
        LocalDate DataAtualizacao
) {
}
