package com.maksymilianst.lightweights.execution.mapper;

import com.maksymilianst.lightweights.execution.dto.TrainingExecutionDto;
import com.maksymilianst.lightweights.execution.model.TrainingExecution;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ExerciseExecutionMapper.class)
public interface TrainingExecutionMapper {
    @Mapping(source = "trainingExecution.referencedTraining.name", target = "referencedTrainingName")
    TrainingExecutionDto toDto(TrainingExecution trainingExecution);


}
