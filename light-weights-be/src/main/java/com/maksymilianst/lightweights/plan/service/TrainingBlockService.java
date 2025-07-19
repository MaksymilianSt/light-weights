package com.maksymilianst.lightweights.plan.service;

import com.maksymilianst.lightweights.plan.dto.TrainingBlockDto;
import com.maksymilianst.lightweights.user.User;


public interface TrainingBlockService {

    TrainingBlockDto create(Integer planId, TrainingBlockDto block, User user);

    TrainingBlockDto update(Integer blockId, TrainingBlockDto block);

    void delete(Integer blockId);
}
