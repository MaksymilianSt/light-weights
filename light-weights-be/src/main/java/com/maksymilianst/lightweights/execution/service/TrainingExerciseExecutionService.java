package com.maksymilianst.lightweights.execution.service;

import com.maksymilianst.lightweights.execution.dto.TrainingExerciseExecutionDto;


public interface TrainingExerciseExecutionService {
    TrainingExerciseExecutionDto update(Integer exerciseExecutionId, TrainingExerciseExecutionDto exerciseExecutionDto);
}
