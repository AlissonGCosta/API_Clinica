package org.costa.API_Clinica.pacientes.repository;

import org.costa.API_Clinica.pacientes.entity.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PacienteRepository extends JpaRepository<PacienteEntity, UUID> {

    Optional<PacienteEntity> findByCpf(String cpf);
    Optional<PacienteEntity> findByEmail(String email);
}
