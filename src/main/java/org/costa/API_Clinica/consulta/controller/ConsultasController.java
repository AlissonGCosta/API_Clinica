package org.costa.API_Clinica.consulta.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.costa.API_Clinica.consulta.dto.request.ConsultasRequestDto;
import org.costa.API_Clinica.consulta.dto.response.ConsultasResponseDto;
import org.costa.API_Clinica.consulta.service.ConsultasService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/consultas")
@RequiredArgsConstructor
public class ConsultasController {

    private final ConsultasService consultasService;

    @PostMapping("/medico/{medId}/paciente/{pacId}/agendar")
    @ResponseStatus(HttpStatus.CREATED)
    public void criarConsulta(@PathVariable UUID medId,
                              @PathVariable UUID pacId,
                              @Valid @RequestBody ConsultasRequestDto dto){
        consultasService.criarConsulta(dto, medId, pacId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ConsultasResponseDto> listarConsultas(){
        return consultasService.listarTodasConsultas();
    }
}
