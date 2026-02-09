package com.mvalentin.agendadortarefas.business.mapper;

import com.mvalentin.agendadortarefas.business.dto.TarefasDto;
import com.mvalentin.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TarefasMapper {
    // O MapStruct converte automaticamente ObjectId para String e vice-versa

    TarefasEntity ToTarefaEntity(TarefasDto tarefasDto);

    TarefasDto toTarefaDto(TarefasEntity tarefasEntity);

    //Caso o nome dos campos seja diferente, você usaria:
    // @Mapping(source = "descricaoTarefa", target = "descricao")
    // TarefaDTO toDTO(Tarefa tarefa);
}