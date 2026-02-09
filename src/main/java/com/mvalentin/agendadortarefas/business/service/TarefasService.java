package com.mvalentin.agendadortarefas.business.service;

import com.mvalentin.agendadortarefas.business.mapper.TarefasMapper;
import com.mvalentin.agendadortarefas.business.dto.TarefasDto;
import com.mvalentin.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.mvalentin.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.mvalentin.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.mvalentin.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final  TarefasRepository tarefasRepository;
    private final TarefasMapper tarefasMapper;
    private final JwtUtil jwtUtil;

    public TarefasDto salvarTarefa (String token,TarefasDto tarefasDto){
        // 1. Extração do e-mail
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        // 2. Preenchimento de campos automáticos (Regra de Negócio)
        tarefasDto.setDataCriacao(LocalDateTime.now());
        tarefasDto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        tarefasDto.setEmailUsuario(email); // precisamos setar o e-mail

        // 3. Conversão DTO -> Entity
        TarefasEntity tarefa = tarefasMapper.ToTarefaEntity(tarefasDto);

        // 4. Persistência no MongoDB
        tarefa = tarefasRepository.save(tarefa);

        // 5. Retorno convertido para DTO
        return tarefasMapper.toTarefaDto(tarefa);
    }
}