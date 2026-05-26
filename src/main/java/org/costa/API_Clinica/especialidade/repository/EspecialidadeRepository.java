package org.costa.API_Clinica.especialidade.repository;

import org.costa.API_Clinica.especialidade.entity.EspcialidadeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EspecialidadeRepository extends JpaRepository<EspcialidadeEntity, UUID> {

    Optional<EspcialidadeEntity> findByNome(String nome);
}
