package org.costa.API_Clinica.pacientes.service;

import org.costa.API_Clinica.config.PasswordConfig;
import org.costa.API_Clinica.consulta.dto.response.ConsultasResponseDto;
import org.costa.API_Clinica.consulta.dto.response.ConsultasResponsePacienteDto;
import org.costa.API_Clinica.exception.ConflictException;
import org.costa.API_Clinica.exception.ResourceNotFoundException;
import org.costa.API_Clinica.exception.UnauthorizedException;
import org.costa.API_Clinica.pacientes.dto.request.*;
import org.costa.API_Clinica.pacientes.dto.response.PacienteResponseDto;
import org.costa.API_Clinica.pacientes.entity.Ativo;
import org.costa.API_Clinica.pacientes.entity.PacienteEntity;
import org.costa.API_Clinica.pacientes.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.costa.API_Clinica.pagamento.dto.response.PagamentoResponse;
import org.costa.API_Clinica.prontuario.dto.response.ProntuarioResponseDto;
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
            throw new ConflictException("Usuario Ja cadastrado");
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
                        paciente.getConsultasPaciente().stream().map(
                                consulta -> new ConsultasResponsePacienteDto(
                                consulta.getId(),
                                consulta.getMedico().getId(),
                                consulta.getPaciente().getId(),
                                consulta.getDataConsulta(),
                                consulta.getHoraConsulta(),
                                consulta.getProntuario().getDiagnostico(),
                                consulta.getConsultaStatus(),
                                consulta.getMotivoCancelamento(),
                                consulta.getDataCriacao(),
                                consulta.getDataAtualizacao()
                        )).toList(),
                        paciente.getPagamento().stream().map(
                                pagamento -> new PagamentoResponse(
                                        pagamento.getId(),
                                        pagamento.getFormaPagamento(),
                                        pagamento.getStatus(),
                                        pagamento.getValor(),
                                        pagamento.getDataVencimento(),
                                        pagamento.getDataPagamento(),
                                        pagamento.getDataCriado()
                                )
                        ).toList()
                )).toList();
    }

    //listando apenas o paciente pelo id inserido
    public Optional<PacienteResponseDto> listarPacientePorId(UUID id) {

        //validando se o cpf existe
        if(pacienteRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Paciente nao encontrado");
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
                        paciente.getConsultasPaciente().stream().map(
                                consulta -> new ConsultasResponsePacienteDto(
                                        consulta.getId(),
                                        consulta.getMedico().getId(),
                                        consulta.getPaciente().getId(),
                                        consulta.getDataConsulta(),
                                        consulta.getHoraConsulta(),
                                        consulta.getProntuario().getDiagnostico(),
                                        consulta.getConsultaStatus(),
                                        consulta.getMotivoCancelamento(),
                                        consulta.getDataCriacao(),
                                        consulta.getDataAtualizacao()
                                )
                        ).toList(),
                        paciente.getPagamento().stream().map(
                                pagamento -> new PagamentoResponse(
                                        pagamento.getId(),
                                        pagamento.getFormaPagamento(),
                                        pagamento.getStatus(),
                                        pagamento.getValor(),
                                        pagamento.getDataVencimento(),
                                        pagamento.getDataPagamento(),
                                        pagamento.getDataCriado()
                                )
                        ).toList()
                )).findFirst();

    }

    //deletar simples do paciente
    public void deletePaciente(UUID id) {
        if(pacienteRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Paciente nao encontrado");
        }
        pacienteRepository.deleteById(id);
    }

    //metodo para alterar nome
    public void alteraNome(PacienteRequestNameDto dto, UUID id) {

        //validando se o id existe
        PacienteEntity nomePaciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente nao encontrado"));

        //alterando o nome do paciente
        nomePaciente.setNome(dto.getNome());
        nomePaciente.setDataAtualizacao(LocalDate.now());

        //salvando o novo nome no repository
        pacienteRepository.save(nomePaciente);

    }

    //metodo para alterar email
    public void alteraEmail(PacienteRequestEmailDto dto, UUID id) {

        //validando
        PacienteEntity emailPaciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente nao encontrado"));

        //alterando
        emailPaciente.setEmail(dto.getEmail());
        emailPaciente.setDataAtualizacao(LocalDate.now());

        //salvando
        pacienteRepository.save(emailPaciente);
    }

    //alterando a senha
    public void aleteraSenha(PacienteRequestSenhaDto dto, UUID id) {

        //validando o cpf
        PacienteEntity senhaPaciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente nao encontrado"));

        //retornando uma comparação das senhas antigas
        boolean senhaValida = passwordConfig.passwordEncoder().matches(dto.getSenhaAntiga(), senhaPaciente.getSenha());
        if(!senhaValida) {
            throw new UnauthorizedException("Senha Invalida");
        }

        //setando nova senha
        senhaPaciente.setSenha(passwordConfig.passwordEncoder().encode(dto.getNovaSenha()));
        senhaPaciente.setDataAtualizacao(LocalDate.now());

        //salvando a senha no repository
        pacienteRepository.save(senhaPaciente);
    }


    //criando um put para alterar todos os atributos
    public void putPaciente(PacienteRequestPutDto dto, UUID id) {

        PacienteEntity novoPaciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente nao encontrado"));

        novoPaciente.setNome(dto.getNome());
        novoPaciente.setEmail(dto.getEmail());
        novoPaciente.setDataAtualizacao(LocalDate.now());

        pacienteRepository.save(novoPaciente);
    }

}
