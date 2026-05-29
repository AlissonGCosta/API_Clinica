package org.costa.API_Clinica.pagamento.dto.request;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class PagamentoRequestDto {


    private BigDecimal valor;
    private String dataVencimento;
    private String dataPagamento;

}
