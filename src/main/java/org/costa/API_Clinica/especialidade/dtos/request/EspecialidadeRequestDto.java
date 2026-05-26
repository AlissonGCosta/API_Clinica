package org.costa.API_Clinica.especialidade.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class EspecialidadeRequestDto {

    @NotBlank
    String nome;
}
