package org.costa.API_Clinica.especialidade.dto.response;

import org.costa.API_Clinica.medicos.dto.response.MedicoResponseDto;
import org.costa.API_Clinica.medicos.dto.response.MedicoResponseEspecialidadeDto;
import org.costa.API_Clinica.medicos.entity.MedicoEntity;


import java.util.List;
import java.util.Set;
import java.util.UUID;

public record EspecialidadeResponseDto(
         UUID id,
         String nome,
         List<MedicoResponseEspecialidadeDto> medicos
) {
}
