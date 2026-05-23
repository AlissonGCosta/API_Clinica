package com.costa.API_Clinica.pacientes.dto.response;

import com.costa.API_Clinica.consulta.entity.ConsultaEntity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public record PacienteResponseDto(
        UUID id,
        String nome,
        String cpf,
        String email,
        LocalDate dataCriacao,
        ConsultaEntity consultasPaciente
) {
    public PacienteResponseDto(UUID id, String nome, String cpf, String email, Set<ConsultaEntity> consultasPaciente) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;

    }
}
