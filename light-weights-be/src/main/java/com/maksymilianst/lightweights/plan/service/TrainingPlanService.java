package com.maksymilianst.lightweights.plan.service;

import com.maksymilianst.lightweights.plan.dto.TrainingPlanDto;
import com.maksymilianst.lightweights.plan.dto.TrainingPlanPreviewDto;
import com.maksymilianst.lightweights.user.User;

import java.util.List;

public interface TrainingPlanService {
    List<TrainingPlanPreviewDto> getAllForUser(Integer userId);

    TrainingPlanDto getById(Integer planId);

    TrainingPlanDto create(TrainingPlanDto plan, User user);

    TrainingPlanDto update(Integer planId, TrainingPlanDto plan);

    void delete(Integer planId);
}
