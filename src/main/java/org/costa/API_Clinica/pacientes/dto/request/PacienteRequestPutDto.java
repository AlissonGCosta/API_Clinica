package org.costa.API_Clinica.pacientes.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PacienteRequestPutDto {

    @NotBlank
    private String nome;

    @NotBlank @Email
    private String email;
}
