package com.costa.API_Clinica.pacientes.controller;

import com.costa.API_Clinica.pacientes.dto.request.PacienteRequestDto;
import com.costa.API_Clinica.pacientes.dto.request.PacienteRequestEmailDto;
import com.costa.API_Clinica.pacientes.dto.request.PacienteRequestNameDto;
import com.costa.API_Clinica.pacientes.dto.request.PacienteRequestSenhaDto;
import com.costa.API_Clinica.pacientes.dto.response.PacienteResponseDto;
import com.costa.API_Clinica.pacientes.service.PacienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("/v1/pacientes")
@RestController
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarPacinete(@Valid @RequestBody PacienteRequestDto dto){
        pacienteService.createPaciente(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PacienteResponseDto> listarPacientes(){
        return pacienteService.listarPaciente();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<PacienteResponseDto> listarPacientePorId(@PathVariable UUID id){
        return pacienteService.listarPacientePorId(id);
    }

    @PatchMapping("/{id}/nomes")
    @ResponseStatus(HttpStatus.OK)
    public void alterarNomePacinete(@Valid @RequestBody PacienteRequestNameDto dto, @PathVariable UUID id){
        pacienteService.alteraNome(dto,id);
    }

    @PatchMapping("/{id}/email")
    @ResponseStatus(HttpStatus.OK)
    public void alterarEmailPaciente(@Valid @RequestBody PacienteRequestEmailDto dto, @PathVariable UUID id){
        pacienteService.alteraEmail(dto,id);
    }

    @PatchMapping("/{id}/senha")
    @ResponseStatus(HttpStatus.OK)
    public void alterarSenhaPaciente(@Valid @RequestBody PacienteRequestSenhaDto dto, @PathVariable UUID id){
        pacienteService.aleteraSenha(dto,id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarPaciente(@PathVariable UUID id){
        pacienteService.deletePaciente(id);
    }

}
