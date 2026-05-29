package org.costa.API_Clinica.consulta.dto.response;

import org.costa.API_Clinica.consulta.entity.ConsultaStatus;
import org.costa.API_Clinica.prontuario.dto.response.ProntuarioResponseDto;
import org.costa.API_Clinica.prontuario.entity.PronturaioEntity;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ConsultasResponsePacienteDto(
        UUID id,
        UUID medico,
        UUID paciente,
        String data_consulta,
        String hora_consulta,
        String prontuario,
        ConsultaStatus consultaStatus,
        String motivoCancelamento,
        LocalDate DataCriacao,
        LocalDate DataAtualizacao
) {
}
