package com.maksymilianst.lightweights.publication.service;

import com.maksymilianst.lightweights.plan.dto.TrainingPlanDto;
import com.maksymilianst.lightweights.publication.dto.PlanScale;
import com.maksymilianst.lightweights.publication.dto.TrainingPlanPublicationDto;

import java.util.List;

public interface PlanPublicationService {

    TrainingPlanPublicationDto getById(Integer publicationId);

    List<TrainingPlanPublicationDto> getAll();

    TrainingPlanPublicationDto publishPlan(Integer planId);

    TrainingPlanDto downloadPlan(Integer planPublicationId, PlanScale scale);

    void deletePublishedPlan(Integer planPublicationId);
}
