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
    private String data_consulta;

    @NotNull
    private String hora_consulta;

    private String motivoCancelamento;
}
