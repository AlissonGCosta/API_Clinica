package org.costa.API_Clinica.pacientes.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class PacienteRequestDto {

    @NotBlank
    private String nome;

    @NotBlank
    private String cpf;

    @NotBlank @Email
    private String email;

    @NotBlank
    private String senha;
}
