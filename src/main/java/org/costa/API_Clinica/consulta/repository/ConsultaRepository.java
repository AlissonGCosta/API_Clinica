package org.costa.API_Clinica.consulta.repository;

import org.costa.API_Clinica.consulta.entity.ConsultaEntity;
import org.costa.API_Clinica.consulta.entity.ConsultaStatus;
import org.costa.API_Clinica.pacientes.entity.PacienteEntity;
import org.costa.API_Clinica.pagamento.entity.StatusPagamentoEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ConsultaRepository extends JpaRepository<ConsultaEntity, UUID> {

    List<ConsultaEntity> findByConsultaStatus(ConsultaStatus status);
}
