package com.mvalentin.agendadortarefas.controller;

import com.mvalentin.agendadortarefas.business.dto.TarefasDto;
import com.mvalentin.agendadortarefas.business.service.TarefasService;
import com.mvalentin.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.mvalentin.agendadortarefas.infrastructure.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefasController {

    private final TarefasService tarefasService;

    @PostMapping
    public ResponseEntity<TarefasDto> salvarTarefa(@RequestBody TarefasDto tarefasDto,
                                                   @RequestHeader("Authorization") String token) {
        // 1. Chama o serviço UMA ÚNICA VEZ e guarda o resultado (o DTO retornado)
        TarefasDto tarefaSalva = tarefasService.salvarTarefa(token, tarefasDto);

        // 2. Retorna a variável que já contém os dados salvos
        return ResponseEntity.ok(tarefaSalva);
    }
    @GetMapping("/eventos")
    public ResponseEntity<List<TarefasDto>> buscaTarefasPorPeriodo(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
                                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal){
        return ResponseEntity.ok(tarefasService.buscaTarefasAgendadorPorPeriodo(dataInicial,dataFinal));
    }

    @GetMapping
    public ResponseEntity<List<TarefasDto>> buscaTarefasPorEmail(@RequestHeader ("Authorization") String token){
        return ResponseEntity.ok(tarefasService.buscaTarefasByEmail(token));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletaTarefaPorId(@RequestParam("id") String id){
        try {
            tarefasService.deletaTarefaById(id);
        }catch (ResourceNotFoundException e ){
            throw new ResourceNotFoundException("Erro ao deletar tarefaa, id inexistnte" + id +
                    e.getCause());
        }

        return ResponseEntity.ok().build();
    }
    @PatchMapping
    public ResponseEntity<TarefasDto> alteraStatusNotificacao(@RequestParam("status") StatusNotificacaoEnum status,
                                                              @RequestParam ("id") String id,
                                                              @RequestHeader ("Authorization") String token){
        return ResponseEntity.ok(tarefasService.alteracaoStatus(status,id));
    }
   // ou posso fazer assim, porém em ambos a situiações é  necessário incluir no insomnia o token
//    @PatchMapping
//    public ResponseEntity<TarefasDto> alteraStatusNotificacao(@RequestParam("status") StatusNotificacaoEnum status,
//                                                              @RequestParam ("id") String id){
//        return ResponseEntity.ok(tarefasService.alteracaoStatus(status,id));
//    }

    @PutMapping
    public ResponseEntity<TarefasDto> updateTarefas(@RequestBody TarefasDto tarefasDto,
                                                    @RequestParam("id") String id){
        return ResponseEntity.ok(tarefasService.updateTarefas(tarefasDto,id));
    }

}