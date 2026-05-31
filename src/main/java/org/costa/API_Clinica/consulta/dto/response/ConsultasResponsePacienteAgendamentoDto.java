package org.costa.API_Clinica.consulta.dto.response;

import org.costa.API_Clinica.consulta.entity.ConsultaStatus;
import org.costa.API_Clinica.pagamento.entity.StatusPagamentoEnum;

import java.time.LocalDate;
import java.util.UUID;

public record ConsultasResponsePacienteAgendamentoDto(
        UUID id,
        UUID medico,
        UUID paciente,
        String data_consulta,
        String hora_consulta,
        ConsultaStatus consultaStatus,
        String motivoCancelamento,
        LocalDate DataCriacao,
        LocalDate DataAtualizacao
) {
}
