package com.maksymilianst.lightweights.plan.service;

import com.maksymilianst.lightweights.plan.dto.TrainingPlanPreviewDto;

import java.util.List;

public interface TrainingPlanService {
    List<TrainingPlanPreviewDto> getAllForUser(Integer userId);
}
