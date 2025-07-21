package com.maksymilianst.lightweights.plan.service;

import com.maksymilianst.lightweights.plan.dto.TrainingDto;

public interface TrainingService {

    TrainingDto getById(Integer trainingId);

    TrainingDto create(Integer blockId, TrainingDto training);

    TrainingDto update(Integer trainingId, TrainingDto training);

    void delete(Integer trainingId);
}
