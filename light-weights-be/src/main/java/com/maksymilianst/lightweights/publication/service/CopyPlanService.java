package com.maksymilianst.lightweights.publication.service;

import com.maksymilianst.lightweights.plan.model.TrainingPlan;
import com.maksymilianst.lightweights.publication.dto.PlanScale;
import com.maksymilianst.lightweights.publication.model.TrainingPlanPublication;

public interface CopyPlanService {

    TrainingPlanPublication fromPlan(TrainingPlan plan);

    TrainingPlan fromPublication(TrainingPlanPublication publication, PlanScale scale);
}
