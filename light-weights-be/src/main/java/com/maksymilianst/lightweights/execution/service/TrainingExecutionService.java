package com.maksymilianst.lightweights.execution.service;

import com.maksymilianst.lightweights.execution.dto.TrainingExecutionDto;
import com.maksymilianst.lightweights.user.User;


public interface TrainingExecutionService {

    TrainingExecutionDto create(Integer trainingId, User user);

    TrainingExecutionDto update(Integer trainingExecutionId, TrainingExecutionDto trainingExecutionDto);

}
