package com.maksymilianst.lightweights.publication.service;

import com.maksymilianst.lightweights.publication.dto.TrainingPlanPublicationOpinionDto;

public interface PlanPublicationOpinionService {

    TrainingPlanPublicationOpinionDto create(Integer planPublicationId, String opinion);

    void delete(Integer planPublicationOpinionId);
}
