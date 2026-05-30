package org.costa.API_Clinica.medicos.dto.response;

import org.costa.API_Clinica.pacientes.entity.Ativo;

import java.time.LocalDate;
import java.util.UUID;

public record MedicoResponseEspecialidadeDto(
        UUID id,
        String nome,
        String crm,
        Ativo estado,
        LocalDate dataCriacao,
        LocalDate dataAtualizacao
) {
}
