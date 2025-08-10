package com.maksymilianst.lightweights.publication.service.impl;

import com.maksymilianst.lightweights.plan.dto.TrainingPlanDto;
import com.maksymilianst.lightweights.plan.mapper.TrainingPlanMapper;
import com.maksymilianst.lightweights.plan.model.TrainingPlan;
import com.maksymilianst.lightweights.plan.repository.TrainingPlanRepository;
import com.maksymilianst.lightweights.publication.dto.PlanScale;
import com.maksymilianst.lightweights.publication.dto.TrainingPlanPublicationDto;
import com.maksymilianst.lightweights.publication.mapper.TrainingPlanPublicationMapper;
import com.maksymilianst.lightweights.publication.model.TrainingPlanPublication;
import com.maksymilianst.lightweights.publication.repository.TrainingPlanPublicationRepository;
import com.maksymilianst.lightweights.publication.service.CopyPlanService;
import com.maksymilianst.lightweights.publication.service.PlanPublicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlanPublicationServiceImpl implements PlanPublicationService {
    private final TrainingPlanRepository planRepository;
    private final TrainingPlanPublicationRepository publicationRepository;
    private final CopyPlanService copyPlanService;
    private final TrainingPlanPublicationMapper planPublicationMapper;
    private final TrainingPlanMapper trainingPlanMapper;

    @Override
    public TrainingPlanPublicationDto getById(Integer publicationId) {
        return publicationRepository.findById(publicationId)
                .map(planPublicationMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Publication not found"));
    }

    @Override
    public List<TrainingPlanPublicationDto> getAll() {
        return publicationRepository.findAll().stream()
                .map(planPublicationMapper::toDto)
                .toList();
    }

    public TrainingPlanPublicationDto publishPlan(Integer planId) {
        TrainingPlan sourcePlan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found"));

        log.info("Publishing plan: {}", planId);

        TrainingPlanPublication publication = copyPlanService.fromPlan(sourcePlan);
        publication = publicationRepository.save(publication);

        return planPublicationMapper.toDto(publication);
    }

    @Override
    public TrainingPlanDto downloadPlan(Integer planPublicationId, PlanScale scale) {
        TrainingPlanPublication sourcePublication = publicationRepository.findById(planPublicationId)
                .orElseThrow(() -> new IllegalArgumentException("Plan Publication not found"));

        log.info("Downloading plan Id: " + planPublicationId + " Scale : " + scale);

        TrainingPlan plan = copyPlanService.fromPublication(sourcePublication, scale);
        plan = planRepository.save(plan);

        return trainingPlanMapper.toDto(plan);
    }

    @Override
    public void deletePublishedPlan(Integer planPublicationId) {
        log.info("Deleting published plan: {}", planPublicationId);
        publicationRepository.deleteById(planPublicationId);
    }
}
