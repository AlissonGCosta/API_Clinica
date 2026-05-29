package org.costa.API_Clinica.pagamento.dto.response;


import org.costa.API_Clinica.pagamento.entity.FormaPagamentoEnum;
import org.costa.API_Clinica.pagamento.entity.StatusPagamentoEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PagamentoResponse(
         UUID id,
         FormaPagamentoEnum formaPagamento,
         StatusPagamentoEnum status,
         BigDecimal valor,
         String dataVencimento,
         String dataPagamento,
         LocalDate dataCriado

) {
}
