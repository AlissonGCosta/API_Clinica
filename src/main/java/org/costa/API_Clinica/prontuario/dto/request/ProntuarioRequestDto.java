package org.costa.API_Clinica.prontuario.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
public class ProntuarioRequestDto {

    @NotBlank
    private String diagnostico;
    @NotBlank
    private String prescricao;
    @NotBlank
    private String observacao;
}
