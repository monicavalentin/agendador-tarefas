package com.mvalentin.agendadortarefas.business.mapper;

import com.mvalentin.agendadortarefas.business.dto.TarefasDto;
import com.mvalentin.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TarefasUpDateMapper {

    void updateTarefas(TarefasDto tarefasDto, @MappingTarget TarefasEntity tarefasEntity);
}
