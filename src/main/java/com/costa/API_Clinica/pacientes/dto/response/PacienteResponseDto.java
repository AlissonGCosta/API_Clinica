package com.costa.API_Clinica.pacientes.dto.response;

import com.costa.API_Clinica.consulta.entity.ConsultaEntity;
import com.costa.API_Clinica.pacientes.entity.Ativo;
import com.costa.API_Clinica.pagamento.entity.PagamentoEntity;

import java.time.LocalDate;
import java.util.HashSet;
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
        Set<ConsultaEntity> consultasPaciente,
        Set<PagamentoEntity> pagamentos
) {
}
