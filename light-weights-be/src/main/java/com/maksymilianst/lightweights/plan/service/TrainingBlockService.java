package com.maksymilianst.lightweights.plan.service;

import com.maksymilianst.lightweights.plan.dto.TrainingBlockDto;


public interface TrainingBlockService {

    TrainingBlockDto getById(Integer blockId);

    TrainingBlockDto create(Integer planId, TrainingBlockDto block);

    TrainingBlockDto update(Integer blockId, TrainingBlockDto block);

    void delete(Integer blockId);
}
