package org.costa.API_Clinica.consulta.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class ConsultasRequestDto {

    @NotNull
    private String dataConsulta;

    @NotNull
    private String horaConsulta;

    private String motivoCancelamento;
}
