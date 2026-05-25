package org.costa.API_Clinica.medicos.service;

import org.costa.API_Clinica.config.PasswordConfig;
import org.costa.API_Clinica.exception.ConflictException;
import org.costa.API_Clinica.exception.ResourceNotFoundException;
import org.costa.API_Clinica.exception.UnauthorizedException;
import org.costa.API_Clinica.medicos.dto.request.MedicoRequestDto;
import org.costa.API_Clinica.medicos.dto.request.MedicoRequestNomeDto;
import org.costa.API_Clinica.medicos.dto.request.MedicoRequestPutDto;
import org.costa.API_Clinica.medicos.dto.request.MedicoRequestSenhaDto;
import org.costa.API_Clinica.medicos.dto.response.MedicoResponseDto;
import org.costa.API_Clinica.medicos.entity.MedicoEntity;
import org.costa.API_Clinica.medicos.repository.MedicosRepository;
import org.costa.API_Clinica.pacientes.entity.Ativo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicoService {

    //camando o repositorio
    private final MedicosRepository medicosRepository;

    //chamando o codificador da senha
    private final PasswordConfig passwordConfig;


    String notfound = "Medico não encontrado";


    //metodo para criar um medico
    public void createMedico(MedicoRequestDto dto){

        //validando se ele ja existe
        if(medicosRepository.findByCrm(dto.getCrm()).isPresent()){
            throw new ConflictException("Medico ja cadastrado com o CRM");
        }

        //criptografando a senha
        String senhaCrpitografada = passwordConfig.passwordEncoder().encode(dto.getSenha());

        //criando o medico
        MedicoEntity medicoEntity = MedicoEntity.builder()
                .nome(dto.getNome())
                .crm(dto.getCrm())
                .senha(senhaCrpitografada)
                .estado(Ativo.ATIVO)
                .dataCriacao(LocalDate.now())
                .dataAtualizacao(LocalDate.now())
                .build();

        //salvando o medico no banco
        medicosRepository.save(medicoEntity);
    }

    //metodo para listar todos os medicos
    public List<MedicoResponseDto> listarMedicos(){

        //dando retorno em todos os medicos encontrados
      return medicosRepository.findAll().stream()
                .map(medico -> new MedicoResponseDto(
                        medico.getId(),
                        medico.getNome(),
                        medico.getCrm(),
                        medico.getEstado(),
                        medico.getDataCriacao(),
                        medico.getDataAtualizacao(),
                        medico.getEspecialidade(),
                        medico.getConsultas()

                )).toList();
    }

    //busca basica pelo id
    public MedicoResponseDto buscarMedicoPorId(UUID id){

        //validando e salvando se achou algo
              MedicoEntity medico =  medicosRepository.findById(id)
                              .orElseThrow(() -> new ResourceNotFoundException(notfound));

                return new MedicoResponseDto(
                        medico.getId(),
                        medico.getNome(),
                        medico.getCrm(),
                        medico.getEstado(),
                        medico.getDataCriacao(),
                        medico.getDataAtualizacao(),
                        medico.getEspecialidade(),
                        medico.getConsultas()
                );

    }

    //delete basico para os testes
    public void deletarMedicoPorId(UUID id){

        //validando e salvando
        MedicoEntity medico = medicosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(notfound));

        //deletando do banco de dados
        medicosRepository.delete(medico);
    }

    //metodo para trocar o nome
    public void alterarNome(MedicoRequestNomeDto dto, UUID id){

        MedicoEntity nomeMedico = medicosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(notfound));

        nomeMedico.setNome(dto.getNome());

        medicosRepository.save(nomeMedico);

    }

    //metodo para trocar a senha
    public void alterarSenha(MedicoRequestSenhaDto dto, UUID id){

        //validando e salvando o medico
        MedicoEntity senhaMedico = medicosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(notfound));

        //verificando se a senha antiga bate
        boolean senhaValida = passwordConfig.passwordEncoder().matches(dto.getSenhaAntiga(), senhaMedico.getSenha());
        if(!senhaValida) {
            throw new UnauthorizedException("Senha Invalida");
        }

        //setando uma nova senha
        senhaMedico.setSenha(passwordConfig.passwordEncoder().encode(dto.getNovaSenha()));

        //salvando a senha no banco de dados
        medicosRepository.save(senhaMedico);
    }


    //criando o put do medicos
    public void putMedico(MedicoRequestPutDto dto, UUID id){

        //validando se o médico existe
        MedicoEntity novoMedico =  medicosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(notfound));

        if(medicosRepository.findByCrm(dto.getCrm()).isPresent()){
            throw new ConflictException("CRM ja cadastrado");
        }


        novoMedico.setNome(dto.getNome());
        novoMedico.setCrm(dto.getCrm());
        novoMedico.setDataAtualizacao(LocalDate.now());
        medicosRepository.save(novoMedico);
    }


}
