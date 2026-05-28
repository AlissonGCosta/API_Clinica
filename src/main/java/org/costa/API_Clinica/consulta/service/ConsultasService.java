package org.costa.API_Clinica.consulta.service;

import lombok.RequiredArgsConstructor;
import org.costa.API_Clinica.consulta.dto.request.ConsultasRequestDto;
import org.costa.API_Clinica.consulta.dto.response.ConsultasResponseDto;
import org.costa.API_Clinica.consulta.entity.ConsultaEntity;
import org.costa.API_Clinica.consulta.entity.ConsultaStatus;
import org.costa.API_Clinica.consulta.repository.ConsultaRepository;
import org.costa.API_Clinica.exception.ConflictException;
import org.costa.API_Clinica.exception.ResourceNotFoundException;
import org.costa.API_Clinica.medicos.entity.MedicoEntity;
import org.costa.API_Clinica.medicos.repository.MedicosRepository;
import org.costa.API_Clinica.pacientes.entity.PacienteEntity;
import org.costa.API_Clinica.pacientes.repository.PacienteRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConsultasService {

    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicosRepository medicosRepository;

    //criando o metodo para criar uma consulta
    public void criarConsulta(ConsultasRequestDto dto, UUID medId, UUID pacID){

        //validando se existe um medico com o id
        MedicoEntity medicoEntity = medicosRepository.findById(medId)
                .orElseThrow(() -> new ResourceNotFoundException("Medico não encontrado"));

        // validando o paciente com o id
        PacienteEntity pacienteEntity = pacienteRepository.findById(pacID)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado"));

        // validando se o medico entity ja tem uma para essa consulta
        boolean checagemDataeHora = medicoEntity.getConsultas().stream()
                .anyMatch(c -> c.getDataConsulta().equals(dto.getDataConsulta())
                && c.getHoraConsulta().equals(dto.getHoraConsulta())
                );
        if(checagemDataeHora){
            throw new ConflictException("ja Existe um consulta Agendada com essa Data e Hora");
        }


        // criando a consulta
        ConsultaEntity novaConsulta =  ConsultaEntity.builder()
                .medico(medicoEntity)
                .paciente(pacienteEntity)
                .dataConsulta(dto.getDataConsulta())
                .horaConsulta(dto.getHoraConsulta())
                .consultaStatus(ConsultaStatus.AGENDADA)
                .motivoCancelamento(dto.getMotivoCancelamento())
                .DataCriacao(LocalDate.now())
                .DataAtualizacao(LocalDate.now())
                .build();

        consultaRepository.save(novaConsulta);
    }

    // criando o metodo para listar todas as consultas
    public List<ConsultasResponseDto> listarTodasConsultas(){

        return consultaRepository.findAll().stream()
                .map(consulta -> new ConsultasResponseDto(
                        consulta.getId(),
                        consulta.getMedico().getId(),
                        consulta.getPaciente().getId(),
                        consulta.getDataConsulta(),
                        consulta.getHoraConsulta(),
                        consulta.getProntuario(),
                        consulta.getConsultaStatus(),
                        consulta.getPagamento(),
                        consulta.getMotivoCancelamento(),
                        consulta.getDataCriacao(),
                        consulta.getDataAtualizacao()
                )).toList();
    }
}
