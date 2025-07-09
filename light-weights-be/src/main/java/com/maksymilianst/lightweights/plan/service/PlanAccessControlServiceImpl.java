package com.maksymilianst.lightweights.plan.service;

import com.maksymilianst.lightweights.plan.repository.TrainingBlockRepository;
import com.maksymilianst.lightweights.plan.repository.TrainingPlanRepository;
import com.maksymilianst.lightweights.plan.repository.TrainingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("planAccessControlService")
@RequiredArgsConstructor
@Slf4j
public class PlanAccessControlServiceImpl implements PlanAccessControlService {

    private final TrainingPlanRepository planRepository;
    private final TrainingBlockRepository blockRepository;
    private final TrainingRepository trainingRepository;

    @Override
    public boolean hasAccessToPlan(Integer planId, Integer userId) {
        boolean hasAccess = planRepository.existsByIdAndUserId(planId, userId);

        if (!hasAccess) {
            log.info("Access denied: User with id {} attempted to access plan with id {}", userId, planId);
        }

        return hasAccess;
    }

    @Override
    public boolean hasAccessToBlock(Integer blockId, Integer userId) {
        boolean hasAccess = blockRepository.existsByBlockIdAndUserId(blockId, userId);

        if (!hasAccess) {
            log.info("Access denied: User with id {} attempted to access block with id {}", userId, blockId);
        }

        return hasAccess;
    }

    @Override
    public boolean hasAccessToTraining(Integer trainingId, Integer userId) {
        boolean hasAccess = trainingRepository.existsByTrainingIdAndUserId(trainingId, userId);

        if (!hasAccess) {
            log.info("Access denied: User with id {} attempted to access training with id {}", userId, trainingId);
        }

        return hasAccess;
    }
}
