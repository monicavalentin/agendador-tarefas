package com.mvalentin.agendadortarefas.controller;

import com.mvalentin.agendadortarefas.business.dto.TarefasDto;
import com.mvalentin.agendadortarefas.business.service.TarefasService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefasController {

    private final TarefasService tarefasService;

    @PostMapping
    public ResponseEntity<TarefasDto> salvarTarefa(@RequestBody TarefasDto tarefasDto,
                                                   @RequestHeader("Authorization") String token ){
        TarefasDto tarefas = tarefasService.salvarTarefa(token, tarefasDto);

        return ResponseEntity.ok(tarefasService.salvarTarefa(token,tarefasDto));
    }
}