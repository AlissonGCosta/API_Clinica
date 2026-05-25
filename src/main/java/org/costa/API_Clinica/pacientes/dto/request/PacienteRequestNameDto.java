package org.costa.API_Clinica.pacientes.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class PacienteRequestNameDto {

    @NotBlank
    String nome;

}
