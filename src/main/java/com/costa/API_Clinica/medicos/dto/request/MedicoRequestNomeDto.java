package com.costa.API_Clinica.medicos.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class MedicoRequestNomeDto {

    @NotBlank
    String  nome;
}
