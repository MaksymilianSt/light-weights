package com.maksymilianst.lightweights.plan.service;

import com.maksymilianst.lightweights.plan.dto.TrainingDto;

public interface TrainingService {
    TrainingDto getByIdForUser(Integer trainingId);
}
