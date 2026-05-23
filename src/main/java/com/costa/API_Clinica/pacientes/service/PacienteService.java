package com.costa.API_Clinica.pacientes.service;

import com.costa.API_Clinica.pacientes.dto.request.PacienteRequestDto;
import com.costa.API_Clinica.pacientes.dto.request.PacienteRequestNameDto;
import com.costa.API_Clinica.pacientes.dto.response.PacienteResponseDto;
import com.costa.API_Clinica.pacientes.entity.PacienteEntity;
import com.costa.API_Clinica.pacientes.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PacienteService {

    //chamando o repostitory do paciente
    private final PacienteRepository pacienteRepository;

    //criando o paciente no banco
    public void createPaciente(PacienteRequestDto dto) {

        //validando se o paciente ja foi cadastrado
        if(pacienteRepository.findByCpf(dto.getCpf()).isPresent() || pacienteRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Usuario Ja cadastrado");
        }

        //padronizando os cpfs
        String cpf = dto.getCpf().replace(".", "").replace("-", "");

        //criando o paciente
        PacienteEntity paciente = PacienteEntity.builder()
                .cpf(cpf)
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .dataCriacao(LocalDate.now())
                .build();


        pacienteRepository.save(paciente);

    }

    //listando todos os pacientes
    public List<PacienteResponseDto> ListarPaciente() {

        //chamando todos os pacientes cadastrados
        return pacienteRepository.findAll().stream()
                .map(paciente -> new PacienteResponseDto(
                        paciente.getId(),
                        paciente.getNome(),
                        paciente.getCpf(),
                        paciente.getEmail(),
                        paciente.getConsultasPaciente()
                )).toList();
    }

    //listando apenas o paciente pelo id inserido
    public Optional<PacienteResponseDto> getPaciente(UUID id) {

        //validando se o cpf existe
        if(!pacienteRepository.findById(id).isPresent()) {
            throw new RuntimeException("Paciente nao encontrado");
        }

        //chamando o paciente pelo Id encontrado
        return pacienteRepository.findById(id).stream()
                .map(paciente -> new PacienteResponseDto(
                        paciente.getId(),
                        paciente.getNome(),
                        paciente.getCpf(),
                        paciente.getEmail(),
                        paciente.getConsultasPaciente()
                )).findFirst();

    }


//    public void alteraNome(PacienteRequestNameDto dto, UUID id) {
//
//        //validando se o id existe
//        if(pacienteRepository.findById(id).isEmpty()) {
//            throw new RuntimeException("Paciente nao encontrado");
//        }
//
//        return pacienteRepository.findById(id).stream()
//                .map(p
//                )
//
//
//
//    }

}
