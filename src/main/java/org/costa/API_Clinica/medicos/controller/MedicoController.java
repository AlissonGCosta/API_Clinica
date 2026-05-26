package org.costa.API_Clinica.medicos.controller;

import org.costa.API_Clinica.medicos.dto.request.MedicoRequestDto;
import org.costa.API_Clinica.medicos.dto.request.MedicoRequestNomeDto;
import org.costa.API_Clinica.medicos.dto.request.MedicoRequestPutDto;
import org.costa.API_Clinica.medicos.dto.request.MedicoRequestSenhaDto;
import org.costa.API_Clinica.medicos.dto.response.MedicoResponseDto;
import org.costa.API_Clinica.medicos.service.MedicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/medicos")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService medicoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarMedico(@Valid @RequestBody MedicoRequestDto dto){
        medicoService.createMedico(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MedicoResponseDto> listarMedicos(){
        return medicoService.listarMedicos();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void putMedico(@PathVariable UUID id, @Valid @RequestBody MedicoRequestPutDto dto){
        medicoService.putMedico(dto, id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MedicoResponseDto buscarMedicoPorId(@PathVariable UUID id){
        return medicoService.buscarMedicoPorId(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarMedicoPorId(@PathVariable UUID id){
        medicoService.deletarMedicoPorId(id);
    }

    @PatchMapping("/{id}/nome")
    @ResponseStatus(HttpStatus.OK)
    public void alterarNome(@PathVariable UUID id, @Valid @RequestBody MedicoRequestNomeDto dto){
        medicoService.alterarNome(dto, id);
    }

    @PatchMapping("/{id}/senha")
    @ResponseStatus(HttpStatus.OK)
    public void alterarNome(@PathVariable UUID id, @Valid @RequestBody MedicoRequestSenhaDto dto){
        medicoService.alterarSenha(dto, id);
    }

    @PostMapping("/especialidade/{espId}/medico/{medId}")
    @ResponseStatus(HttpStatus.OK)
    public void setarEspecialidadeMedico(@PathVariable UUID espId, @PathVariable UUID medId){
        medicoService.escolherEspecialidadePorId(espId, medId);
    }

}
