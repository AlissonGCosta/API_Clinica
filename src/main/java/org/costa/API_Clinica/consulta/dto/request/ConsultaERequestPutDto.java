package org.costa.API_Clinica.consulta.dto.request;

import lombok.*;
import org.costa.API_Clinica.pagamento.dto.request.PagamentoRequestDto;
import org.costa.API_Clinica.prontuario.dto.request.ProntuarioRequestDto;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class ConsultaERequestPutDto {

    ProntuarioRequestDto prontuario;
    PagamentoRequestDto pagamento;

}
