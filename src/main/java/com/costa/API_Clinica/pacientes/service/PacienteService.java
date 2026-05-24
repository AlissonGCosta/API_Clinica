package com.costa.API_Clinica.pacientes.service;

import com.costa.API_Clinica.config.PasswordConfig;
import com.costa.API_Clinica.pacientes.dto.request.PacienteRequestDto;
import com.costa.API_Clinica.pacientes.dto.request.PacienteRequestEmailDto;
import com.costa.API_Clinica.pacientes.dto.request.PacienteRequestNameDto;
import com.costa.API_Clinica.pacientes.dto.request.PacienteRequestSenhaDto;
import com.costa.API_Clinica.pacientes.dto.response.PacienteResponseDto;
import com.costa.API_Clinica.pacientes.entity.Ativo;
import com.costa.API_Clinica.pacientes.entity.PacienteEntity;
import com.costa.API_Clinica.pacientes.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PacienteService {

    //chamando o repostitory do paciente
    private final PacienteRepository pacienteRepository;

    private final PasswordConfig passwordConfig;

    //criando o paciente no banco
    public void createPaciente(PacienteRequestDto dto) {

        //validando se o paciente ja foi cadastrado
        if(pacienteRepository.findByCpf(dto.getCpf()).isPresent() || pacienteRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Usuario Ja cadastrado");
        }

        //padronizando os cpfs
        String cpf = dto.getCpf().replace(".", "").replace("-", "");
        String senhaCriptografada = passwordConfig.passwordEncoder().encode(dto.getSenha());

        //criando o paciente
        PacienteEntity paciente = PacienteEntity.builder()
                .cpf(cpf)
                .email(dto.getEmail())
                .nome(dto.getNome())
                .senha(senhaCriptografada)
                .dataCriacao(LocalDate.now())
                .dataAtualizacao(LocalDate.now())
                .estado(Ativo.ATIVO)
                .build();


        pacienteRepository.save(paciente);

    }

    //listando todos os pacientes
    public List<PacienteResponseDto> listarPaciente() {

        //chamando todos os pacientes cadastrados
        return pacienteRepository.findAll().stream()
                .map(paciente -> new PacienteResponseDto(
                        paciente.getId(),
                        paciente.getNome(),
                        paciente.getCpf(),
                        paciente.getEmail(),
                        paciente.getDataCriacao(),
                        paciente.getEstado(),
                        paciente.getDataAtualizacao(),
                        paciente.getConsultasPaciente(),
                        paciente.getPagamento()
                )).toList();
    }

    //listando apenas o paciente pelo id inserido
    public Optional<PacienteResponseDto> getPaciente(UUID id) {

        //validando se o cpf existe
        if(pacienteRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Paciente nao encontrado");
        }

        //chamando o paciente pelo Id encontrado
        return pacienteRepository.findById(id).stream()
                .map(paciente -> new PacienteResponseDto(
                        paciente.getId(),
                        paciente.getNome(),
                        paciente.getCpf(),
                        paciente.getEmail(),
                        paciente.getDataCriacao(),
                        paciente.getEstado(),
                        paciente.getDataAtualizacao(),
                        paciente.getConsultasPaciente(),
                        paciente.getPagamento()
                )).findFirst();

    }

    //deletar simples do paciente
    public void deletePaciente(UUID id) {
        if(pacienteRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Paciente nao encontrado");
        }
        pacienteRepository.deleteById(id);
    }

    //metodo para alterar nome
    public void alteraNome(PacienteRequestNameDto dto, UUID id) {

        //validando se o id existe
        PacienteEntity nomePaciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente nao encontrado"));

        //alterando o nome do paciente
        nomePaciente.setNome(dto.getNome());

        //salvando o novo nome no repository
        pacienteRepository.save(nomePaciente);

    }

    //metodo para alterar email
    public void alteraEmail(PacienteRequestEmailDto dto, UUID id) {

        //validando
        PacienteEntity emailPaciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente nao encontrado"));

        //alterando
        emailPaciente.setEmail(dto.getEmail());

        //salvando
        pacienteRepository.save(emailPaciente);
    }

    //alterando a senha
    public void aleteraSenha(PacienteRequestSenhaDto dto, UUID id) {

        //validando o cpf
        PacienteEntity senhaPaciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente nao encontrado"));

        //retornando uma comparação das senhas antigas
        boolean senhaValida = passwordConfig.passwordEncoder().matches(dto.getSenhaAntiga(), senhaPaciente.getSenha());
        if(!senhaValida) {
            throw new RuntimeException("Senha Invalida");
        }

        //setando nova senha
        senhaPaciente.setSenha(passwordConfig.passwordEncoder().encode(dto.getNovaSenha()));

    }

}
