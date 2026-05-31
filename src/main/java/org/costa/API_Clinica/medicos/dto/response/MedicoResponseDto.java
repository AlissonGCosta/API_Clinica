package org.costa.API_Clinica.medicos.dto.response;

import org.costa.API_Clinica.consulta.dto.response.ConsultasResponseAgendadoDto;
import org.costa.API_Clinica.especialidade.entity.EspcialidadeEntity;
import org.costa.API_Clinica.pacientes.entity.Ativo;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record MedicoResponseDto(
        UUID id,
        String nome,
        String crm,
        Ativo estado,
        LocalDate dataCriacao,
        LocalDate dataAtualizacao,
        EspcialidadeEntity especialidade,
        List<ConsultasResponseAgendadoDto> consultas
) {
}
