package com.maksymilianst.lightweights.execution.mapper;


import com.maksymilianst.lightweights.execution.dto.TrainingSetExecutionDto;
import com.maksymilianst.lightweights.execution.model.TrainingSetExecution;
import com.maksymilianst.lightweights.plan.model.TrainingSet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface SetExecutionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "referencedSet", source = "trainingSet")
    TrainingSetExecution toNewEntity(TrainingSet trainingSet);

    TrainingSetExecutionDto toDto(TrainingSetExecution trainingSetExecution);

    default List<TrainingSetExecutionDto> toDtos(Set<TrainingSetExecution> setExecutions) {
        return setExecutions.stream()
                .sorted(Comparator.comparing(TrainingSetExecution::getSequence))
                .map(this::toDto)
                .toList();
    }
}
