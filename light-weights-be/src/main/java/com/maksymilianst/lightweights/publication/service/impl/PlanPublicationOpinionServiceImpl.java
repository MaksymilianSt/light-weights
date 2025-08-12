package com.maksymilianst.lightweights.publication.service.impl;

import com.maksymilianst.lightweights.publication.dto.TrainingPlanPublicationOpinionDto;
import com.maksymilianst.lightweights.publication.mapper.TrainingPlanPublicationOpinionMapper;
import com.maksymilianst.lightweights.publication.model.TrainingPlanPublication;
import com.maksymilianst.lightweights.publication.model.TrainingPlanPublicationOpinion;
import com.maksymilianst.lightweights.publication.repository.TrainingPlanPublicationOpinionRepository;
import com.maksymilianst.lightweights.publication.repository.TrainingPlanPublicationRepository;
import com.maksymilianst.lightweights.publication.service.PlanPublicationOpinionService;
import com.maksymilianst.lightweights.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlanPublicationOpinionServiceImpl implements PlanPublicationOpinionService {

    private final TrainingPlanPublicationOpinionRepository opinionRepository;
    private final TrainingPlanPublicationRepository planPublicationRepository;
    private final TrainingPlanPublicationOpinionMapper opinionMapper;

    @Override
    public TrainingPlanPublicationOpinionDto create(Integer planPublicationId, String content) {
        TrainingPlanPublication planPublication = planPublicationRepository.findById(planPublicationId)
                .orElseThrow(() -> new IllegalArgumentException("Plan publication id " + planPublicationId + " does not exist"));

        TrainingPlanPublicationOpinion newOpinion = new TrainingPlanPublicationOpinion();

        newOpinion.setPlanPublication(planPublication);
        newOpinion.setUser(
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        );
        newOpinion.setContent(content);

        newOpinion = opinionRepository.save(newOpinion);
        return opinionMapper.toDto(newOpinion);
    }

    @Override
    public void delete(Integer planPublicationOpinionId) {
        log.info("Deleting plan publication opinion with id {}", planPublicationOpinionId);
        opinionRepository.deleteById(planPublicationOpinionId);
    }
}
