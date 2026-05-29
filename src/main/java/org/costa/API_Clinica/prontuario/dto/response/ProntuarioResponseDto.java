package org.costa.API_Clinica.prontuario.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record ProntuarioResponseDto(
         UUID id,
         String diagnostico,
         String prescricao,
         String observacao,
         LocalDate datacriacao
) {
}
