package org.costa.API_Clinica.pagamento.repository;

import org.costa.API_Clinica.pagamento.entity.PagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PagamentoRepository extends JpaRepository <PagamentoEntity, UUID> {
}
