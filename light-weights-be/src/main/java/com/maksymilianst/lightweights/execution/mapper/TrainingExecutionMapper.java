package com.maksymilianst.lightweights.execution.mapper;

import com.maksymilianst.lightweights.execution.dto.TrainingExecutionDto;
import com.maksymilianst.lightweights.execution.model.TrainingExecution;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ExerciseExecutionMapper.class)
public interface TrainingExecutionMapper {
    TrainingExecutionDto toDto(TrainingExecution trainingExecution);
}
