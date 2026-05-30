package org.costa.API_Clinica.especialidade.service;

import lombok.RequiredArgsConstructor;
import org.costa.API_Clinica.especialidade.dto.request.EspecialidadeRequestDto;
import org.costa.API_Clinica.especialidade.dto.response.EspecialidadeResponseDto;
import org.costa.API_Clinica.especialidade.entity.EspcialidadeEntity;
import org.costa.API_Clinica.especialidade.repository.EspecialidadeRepository;
import org.costa.API_Clinica.exception.ConflictException;
import org.costa.API_Clinica.medicos.dto.response.MedicoResponseDto;
import org.costa.API_Clinica.medicos.dto.response.MedicoResponseEspecialidadeDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EspecialidadeService {

    private final EspecialidadeRepository especialidadeRepository;

    // criando a especialidade
    public void criarEspecialidade(EspecialidadeRequestDto dto) {
        //validando se ja existe em banco
        if(especialidadeRepository.findByNome(dto.getNome()).isPresent()) {
            throw new ConflictException("Especialidade Ja existente");
        }

        //criando a gigante especialidade e salvando a gigantesca especialidade
        especialidadeRepository.save(EspcialidadeEntity.builder()
                        .nome(dto.getNome())
                        .build());
    }

    //metodo para listar todas as especialidades
    public List<EspecialidadeResponseDto> listarEspecialidades(){

        //procurando e listando todas as especialidades
       return especialidadeRepository.findAll().stream()
                .map( esp -> new EspecialidadeResponseDto(
                                esp.getId(),
                                esp.getNome(),
                                esp.getMedicos().stream().map(
                                        medico -> new MedicoResponseEspecialidadeDto(
                                                medico.getId(),
                                                medico.getNome(),
                                                medico.getCrm(),
                                                medico.getEstado(),
                                                medico.getDataCriacao(),
                                                medico.getDataAtualizacao()

                                        )
                                ).toList()
                        )
                ).toList();
    }
}
