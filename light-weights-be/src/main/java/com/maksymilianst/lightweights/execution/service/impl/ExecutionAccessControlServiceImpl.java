package com.maksymilianst.lightweights.execution.service.impl;


import com.maksymilianst.lightweights.execution.repository.TrainingExecutionRepository;
import com.maksymilianst.lightweights.execution.service.ExecutionAccessControlService;
import com.maksymilianst.lightweights.plan.service.PlanAccessControlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("executionAccessControlService")
@RequiredArgsConstructor
@Slf4j
public class ExecutionAccessControlServiceImpl implements ExecutionAccessControlService {

    private final PlanAccessControlService planAccessControlService;
    private final TrainingExecutionRepository trainingExecutionRepository;

    @Override
    public boolean hasAccessToExecution(Integer executionId, Integer userId) {
        boolean hasAccess = trainingExecutionRepository.findById(executionId)
                .map(execution -> {
                    var trainingId = execution.getReferencedTraining().getId();
                    return planAccessControlService.hasAccessToTraining(trainingId, userId);
                })
                .orElse(false);

        if (!hasAccess) {
            log.info("Access denied: User with id {} attempted to access execution with id {}", userId, executionId);
        }

        return hasAccess;
    }
}
