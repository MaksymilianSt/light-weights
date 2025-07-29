package com.maksymilianst.lightweights.execution.service;

import com.maksymilianst.lightweights.execution.dto.TrainingExecutionDto;
import com.maksymilianst.lightweights.user.User;

import java.util.List;


public interface TrainingExecutionService {

    List<TrainingExecutionDto> getAllForUser(User user);

    TrainingExecutionDto getById(Integer trainingExecutionId);

    TrainingExecutionDto finish(Integer trainingExecutionId);

    TrainingExecutionDto create(Integer trainingId, User user);

    TrainingExecutionDto update(Integer trainingExecutionId, TrainingExecutionDto trainingExecutionDto);

    void deleteById(Integer trainingExecutionId);

}
