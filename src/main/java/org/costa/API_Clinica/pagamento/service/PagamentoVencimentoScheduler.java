package org.costa.API_Clinica.pagamento.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.costa.API_Clinica.pagamento.entity.PagamentoEntity;
import org.costa.API_Clinica.pagamento.entity.StatusPagamentoEnum;
import org.costa.API_Clinica.pagamento.repository.PagamentoRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PagamentoVencimentoScheduler {

    private final PagamentoRepository pagamentoRepository;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void verificarPagamentosVencidos(){
        List<PagamentoEntity> pendentes = pagamentoRepository.findByStatus(StatusPagamentoEnum.PENDENTE);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate hoje = LocalDate.now();

        for(PagamentoEntity pagamento : pendentes){
            LocalDate vencimento = LocalDate.parse(pagamento.getDataVencimento(), formatter);

            if(vencimento.isBefore(hoje)){
                pagamento.setStatus(StatusPagamentoEnum.PENDENTE);
            }
        }

    }
}
