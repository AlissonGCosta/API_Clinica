package org.costa.API_Clinica.medicos.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicoRequestPutDto {

    @NotBlank
    private String nome;

    @NotBlank
    private String crm;

}
