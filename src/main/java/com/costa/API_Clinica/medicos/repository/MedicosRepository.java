package com.costa.API_Clinica.medicos.repository;

import com.costa.API_Clinica.medicos.entity.MedicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MedicosRepository extends JpaRepository<MedicoEntity, UUID> {

    Optional<MedicoEntity>  findByCrm(String crm);
}
