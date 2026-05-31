package org.costa.API_Clinica.consulta.service;

import lombok.RequiredArgsConstructor;
import org.costa.API_Clinica.consulta.dto.request.ConsultaERequestPutDto;
import org.costa.API_Clinica.consulta.dto.request.ConsultasRequestDto;
import org.costa.API_Clinica.consulta.dto.response.ConsultasResponseAgendadoDto;
import org.costa.API_Clinica.consulta.dto.response.ConsultasResponseDto;
import org.costa.API_Clinica.consulta.dto.response.ConsultasResponsePacienteDto;
import org.costa.API_Clinica.consulta.entity.ConsultaEntity;
import org.costa.API_Clinica.consulta.entity.ConsultaStatus;
import org.costa.API_Clinica.consulta.repository.ConsultaRepository;
import org.costa.API_Clinica.exception.ConflictException;
import org.costa.API_Clinica.exception.ResourceNotFoundException;
import org.costa.API_Clinica.medicos.entity.MedicoEntity;
import org.costa.API_Clinica.medicos.repository.MedicosRepository;
import org.costa.API_Clinica.pacientes.entity.PacienteEntity;
import org.costa.API_Clinica.pacientes.repository.PacienteRepository;
import org.costa.API_Clinica.pagamento.entity.FormaPagamentoEnum;
import org.costa.API_Clinica.pagamento.entity.PagamentoEntity;
import org.costa.API_Clinica.pagamento.entity.StatusPagamentoEnum;
import org.costa.API_Clinica.pagamento.repository.PagamentoRepository;
import org.costa.API_Clinica.prontuario.dto.response.ProntuarioResponseDto;
import org.costa.API_Clinica.prontuario.entity.PronturaioEntity;
import org.costa.API_Clinica.prontuario.repository.ProntuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConsultasService {

    // chamando os repositorios
    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicosRepository medicosRepository;
    private final ProntuarioRepository prontuarioRepository;
    private final PagamentoRepository pagamentoRepository;

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

        // validando se o paciente ja tem consulta para esse hora e data
        boolean checagemDataeHoraPaciente = pacienteEntity.getConsultasPaciente().stream()
                .anyMatch(c -> c.getDataConsulta().equals(dto.getDataConsulta())
                && c.getHoraConsulta().equals(dto.getHoraConsulta()));

        if(checagemDataeHoraPaciente){
            throw new ConflictException("ja Existe um consulta Agendada com essa Data e Hora");
        }


        // validando a validade e status do paciente
        Optional<PagamentoEntity> pagamentoPendendte = pacienteEntity.getPagamento().stream()
                .filter(pag -> pag.getStatus() == StatusPagamentoEnum.PENDENTE)
                .findFirst();

        if(pagamentoPendendte.isPresent()){
            String dataVencimento = pagamentoPendendte.get().getDataVencimento();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate vencimento = LocalDate.parse(dataVencimento, formatter);
            if (vencimento.isBefore(LocalDate.now())) {
                throw new ConflictException("paciente n pode estar em debito");
            }
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

    // criando o metodo para listar todas as consultas agendadas
    public List<ConsultasResponseAgendadoDto> listarTodasConsultas(){

        return consultaRepository.findAll().stream()
                .map(consulta -> new ConsultasResponseAgendadoDto(
                        consulta.getId(),
                        consulta.getMedico().getId(),
                        consulta.getPaciente().getId(),
                        consulta.getDataConsulta(),
                        consulta.getHoraConsulta(),
                        consulta.getConsultaStatus(),
                        consulta.getMotivoCancelamento(),
                        consulta.getDataCriacao(),
                        consulta.getDataAtualizacao()
                )).toList();
    }

    // criando o metodo para listar consultas apenas concluidas
    public List<ConsultasResponseDto> listarConusltasConluidas(){


        return consultaRepository.findByConsultaStatus(ConsultaStatus.CONCLUIDA).stream()
                .map(consulta -> new ConsultasResponseDto(
                        consulta.getId(),
                        consulta.getMedico().getId(),
                        consulta.getPaciente().getId(),
                        consulta.getDataConsulta(),
                        consulta.getHoraConsulta(),
                        consulta.returnProntuario(),
                        consulta.getConsultaStatus(),
                        consulta.getPagamento().getStatus(),
                        consulta.getMotivoCancelamento(),
                        consulta.getDataCriacao(),
                        consulta.getDataAtualizacao()
                )).toList();
    }

    // metodo para alterar o status da consutla e criar um prontuario
    @Transactional
    public void altearConsuta(ConsultaERequestPutDto dto, UUID id){

        //validando e salvando
        ConsultaEntity consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta inexistente"));


        // verificando se ja esta concluida
        if(consulta.getConsultaStatus().equals(ConsultaStatus.CONCLUIDA)){
            throw new ConflictException("Consulta ja Concluida!");
        }

        // verificando se ja esta cancelada
        if(consulta.getConsultaStatus().equals(ConsultaStatus.VENCIDO)){
            throw new ConflictException("Conulta n pode ser concluida");
        }
        // setando novo status
        consulta.setConsultaStatus(ConsultaStatus.CONCLUIDA);
        consulta.setDataAtualizacao(LocalDate.now());

        // salvando no banco
        consultaRepository.save(consulta);

            // criando o prontuário
            PronturaioEntity novoProntuario = PronturaioEntity.builder()
                    .consulta(consulta)
                    .observacao(dto.getProntuario().getObservacao())
                    .diagnostico(dto.getProntuario().getDiagnostico())
                    .prescricao(dto.getProntuario().getPrescricao())
                    .datacriacao(LocalDate.now())
                    .build();
            prontuarioRepository.save(novoProntuario);

        //criando o pagamento

        PacienteEntity novoPaciente = consulta.getPaciente();
        PagamentoEntity novoPagamento = PagamentoEntity.builder()
                .paciente(novoPaciente)
                .consulta(consulta)
                .dataPagamento(dto.getPagamento().getDataPagamento())
                .formaPagamento(FormaPagamentoEnum.PIX)
                .valor(dto.getPagamento().getValor())
                .status(StatusPagamentoEnum.PENDENTE)
                .dataCriado(LocalDate.now())
                .dataVencimento(dto.getPagamento().getDataVencimento())
                .build();
        pagamentoRepository.save(novoPagamento);

    }
}
