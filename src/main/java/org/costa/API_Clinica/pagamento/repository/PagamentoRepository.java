package org.costa.API_Clinica.pagamento.repository;

import org.costa.API_Clinica.pagamento.entity.PagamentoEntity;
import org.costa.API_Clinica.pagamento.entity.StatusPagamentoEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PagamentoRepository extends JpaRepository <PagamentoEntity, UUID> {
    List<PagamentoEntity> findByStatus(StatusPagamentoEnum status);

}
