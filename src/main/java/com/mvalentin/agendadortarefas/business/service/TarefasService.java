package com.mvalentin.agendadortarefas.business.service;

import com.mvalentin.agendadortarefas.business.mapper.TarefasMapper;
import com.mvalentin.agendadortarefas.business.dto.TarefasDto;
import com.mvalentin.agendadortarefas.business.mapper.TarefasUpDateMapper;
import com.mvalentin.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.mvalentin.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.mvalentin.agendadortarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.mvalentin.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.mvalentin.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final  TarefasRepository tarefasRepository;
    private final TarefasMapper tarefasMapper;
    private final JwtUtil jwtUtil;
    private final TarefasUpDateMapper tarefasUpDateMapper;

    public TarefasDto salvarTarefa (String token,TarefasDto tarefasDto){
        // 1. Extração do e-mail
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        // 2. Preenchimento de campos automáticos (Regra de Negócio)
        tarefasDto.setDataCriacao(LocalDateTime.now());
        tarefasDto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        tarefasDto.setEmailUsuario(email); // precisamos setar o e-mail

        // 3. Conversão DTO -> Entity
        TarefasEntity tarefa = tarefasMapper.paraTarefaEntity(tarefasDto);

        // 4. Persistência no MongoDB
        tarefa = tarefasRepository.save(tarefa);

        // 5. Retorno convertido para DTO
        return tarefasMapper.paraTarefaDto(tarefa);
    }

    public List<TarefasDto> buscaTarefasAgendadorPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal){
        return tarefasMapper.paraListaTarefasDto(
                tarefasRepository.findByDataEventoBetween(dataInicial,dataFinal));
    }

    public List<TarefasDto> buscaTarefasByEmail (String token){
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        return tarefasMapper.paraListaTarefasDto(
                tarefasRepository.findByEmailUsuario(email)
        );
    }

    public void deletaTarefaById(String id){
        tarefasRepository.deleteById(id);
    }

    public TarefasDto alteracaoStatus(StatusNotificacaoEnum status,String id) {
        try {
            TarefasEntity tarefa = tarefasRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada" + id));
            tarefa.setStatusNotificacaoEnum(status);
            return tarefasMapper.paraTarefaDto(tarefasRepository.save(tarefa));

        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar status da tarefa", e.getCause());
        }

    }
    public TarefasDto updateTarefas(TarefasDto tarefasDto,String id){
        try {
            TarefasEntity tarefas = tarefasRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada" +id));
            tarefasUpDateMapper.updateTarefas(tarefasDto,tarefas);
            return tarefasMapper.paraTarefaDto(tarefasRepository.save(tarefas));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Erro ao alterar status da tarefa" + e.getCause());
        }

    }

}