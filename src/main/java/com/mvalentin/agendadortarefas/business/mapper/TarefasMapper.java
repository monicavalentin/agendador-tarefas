package com.mvalentin.agendadortarefas.business.mapper;

import com.mvalentin.agendadortarefas.business.dto.TarefasDto;
import com.mvalentin.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefasMapper {
    // O MapStruct converte automaticamente ObjectId para String e vice-versa
    @Mapping(source = "id", target = "id")
    @Mapping(source = "dataEvento", target = "dataEvento")
    @Mapping(source = "dataCriacao", target = "dataCriacao")

    TarefasEntity paraTarefaEntity(TarefasDto tarefasDto);

    TarefasDto paraTarefaDto(TarefasEntity tarefasEntity);

    List<TarefasEntity> paraListaTarefaEntity(List<TarefasDto> tarefasDtoList);

    List<TarefasDto> paraListaTarefasDto(List<TarefasEntity> tarefasEntityList);

}