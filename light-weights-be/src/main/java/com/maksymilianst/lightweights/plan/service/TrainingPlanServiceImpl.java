package com.maksymilianst.lightweights.plan.service;

import com.maksymilianst.lightweights.plan.dto.TrainingPlanPreviewDto;
import com.maksymilianst.lightweights.plan.mapper.TrainingPlanPreviewMapper;
import com.maksymilianst.lightweights.plan.model.TrainingPlan;
import com.maksymilianst.lightweights.plan.repository.TrainingPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingPlanServiceImpl implements TrainingPlanService {

    private final TrainingPlanRepository trainingPlanRepository;
    private final TrainingPlanPreviewMapper trainingPlanPreviewMapper;

    public List<TrainingPlanPreviewDto> getAllForUser(Integer userId) {
        return trainingPlanRepository
                .findAllByUserId(userId).stream()
                    .sorted(byMostRecentlyModified())
                    .map(trainingPlanPreviewMapper::toDto)
                    .toList();
    }

    private static Comparator<TrainingPlan> byMostRecentlyModified() {
        return Comparator.comparing((TrainingPlan plan) ->
                plan.getAuditInfo().getUpdatedAt() != null
                        ? plan.getAuditInfo().getUpdatedAt()
                        : plan.getAuditInfo().getCreatedAt()
        ).reversed();
    }
}
