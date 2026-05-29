package org.costa.API_Clinica.pacientes.dto.response;

import org.costa.API_Clinica.consulta.dto.response.ConsultasResponseDto;
import org.costa.API_Clinica.consulta.dto.response.ConsultasResponsePacienteDto;
import org.costa.API_Clinica.consulta.entity.ConsultaEntity;
import org.costa.API_Clinica.pacientes.entity.Ativo;
import org.costa.API_Clinica.pagamento.dto.response.PagamentoResponse;
import org.costa.API_Clinica.pagamento.entity.PagamentoEntity;
import org.costa.API_Clinica.prontuario.dto.response.ProntuarioResponseDto;
import org.costa.API_Clinica.prontuario.entity.PronturaioEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record PacienteResponseDto(
        UUID id,
        String nome,
        String cpf,
        String email,
        LocalDate dataCriacao,
        Ativo estado,
        LocalDate dataAtualizacao,
        List<ConsultasResponsePacienteDto> consultasPaciente,
        List<PagamentoResponse> pagamentos
) {
}
