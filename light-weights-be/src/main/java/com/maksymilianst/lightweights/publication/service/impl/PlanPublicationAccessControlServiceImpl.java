package com.maksymilianst.lightweights.publication.service.impl;

import com.maksymilianst.lightweights.publication.repository.TrainingPlanPublicationRepository;
import com.maksymilianst.lightweights.publication.service.PlanPublicationAccessControlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("planPublicationAccessControlService")
@RequiredArgsConstructor
@Slf4j
class PlanPublicationAccessControlServiceImpl implements PlanPublicationAccessControlService {

    private final TrainingPlanPublicationRepository planPublicationRepository;

    @Override
    public boolean hasRemoveAccessToPlanPublication(Integer publicationId, Integer authorId) {
        Boolean hasAccess = planPublicationRepository.existsByIdAndAuthorId(publicationId, authorId);

        if (!hasAccess) {
            log.info("Access denied: User with id {} attempted to remove plan publication with id {}", authorId, publicationId);
        }

        return hasAccess;
    }
}
