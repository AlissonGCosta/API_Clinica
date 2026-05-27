package org.costa.API_Clinica.consulta.repository;

import org.costa.API_Clinica.consulta.entity.ConsultaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConsultaRepository extends JpaRepository<ConsultaEntity, UUID> {
}
