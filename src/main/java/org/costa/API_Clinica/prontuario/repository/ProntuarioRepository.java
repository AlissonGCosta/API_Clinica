package org.costa.API_Clinica.prontuario.repository;

import org.costa.API_Clinica.prontuario.entity.PronturaioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProntuarioRepository extends JpaRepository<PronturaioEntity, UUID> {
}
