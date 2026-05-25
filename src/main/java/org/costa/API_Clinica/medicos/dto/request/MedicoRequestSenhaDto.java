package org.costa.API_Clinica.medicos.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Validated
public class MedicoRequestSenhaDto {

    @NotBlank
    String  senhaAntiga;
    @NotBlank
    String  novaSenha;

}
