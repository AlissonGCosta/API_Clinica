package org.costa.API_Clinica.especialidade.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.costa.API_Clinica.especialidade.dtos.request.EspecialidadeRequestDto;
import org.costa.API_Clinica.especialidade.dtos.response.EspecialidadeResponseDto;
import org.costa.API_Clinica.especialidade.service.EspecialidadeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/especialidades")
@RequiredArgsConstructor
public class EspecialidadeController {

    private final EspecialidadeService especialidadeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarEspecialidade(@RequestBody EspecialidadeRequestDto dto){
        especialidadeService.criarEspecialidade(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EspecialidadeResponseDto> listarEspecialidades(){
        return especialidadeService.listarEspecialidades();
    }

}
