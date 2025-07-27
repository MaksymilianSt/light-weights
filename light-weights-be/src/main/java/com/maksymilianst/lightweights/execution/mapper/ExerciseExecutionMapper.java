package com.maksymilianst.lightweights.execution.mapper;

import com.maksymilianst.lightweights.execution.dto.TrainingExerciseExecutionDto;
import com.maksymilianst.lightweights.execution.model.TrainingExerciseExecution;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = SetExecutionMapper.class)
public interface ExerciseExecutionMapper {

    @Mapping(source = "exercise.name", target = "name")
    TrainingExerciseExecutionDto toDto(TrainingExerciseExecution trainingExerciseExecution);

    default List<TrainingExerciseExecutionDto> toDtos(Set<TrainingExerciseExecution> eExerciseExecutions) {
        return eExerciseExecutions.stream()
                .sorted(Comparator.comparing(TrainingExerciseExecution::getSequence))
                .map(this::toDto)
                .toList();
    }
}
