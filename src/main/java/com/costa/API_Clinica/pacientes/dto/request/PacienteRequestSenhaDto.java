package com.costa.API_Clinica.pacientes.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class PacienteRequestSenhaDto {

    @NotBlank
    String senhaAntiga;

    @NotBlank
    String novaSenha;
}
