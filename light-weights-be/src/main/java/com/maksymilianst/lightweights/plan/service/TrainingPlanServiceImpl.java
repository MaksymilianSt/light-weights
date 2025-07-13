package com.maksymilianst.lightweights.plan.service;

import com.maksymilianst.lightweights.plan.dto.TrainingPlanDto;
import com.maksymilianst.lightweights.plan.dto.TrainingPlanPreviewDto;
import com.maksymilianst.lightweights.plan.mapper.TrainingPlanMapper;
import com.maksymilianst.lightweights.plan.mapper.TrainingPlanPreviewMapper;
import com.maksymilianst.lightweights.plan.model.DifficultyLevel;
import com.maksymilianst.lightweights.plan.model.PlanCategory;
import com.maksymilianst.lightweights.plan.model.TrainingPlan;
import com.maksymilianst.lightweights.plan.repository.PlanCategoryRepository;
import com.maksymilianst.lightweights.plan.repository.TrainingPlanRepository;
import com.maksymilianst.lightweights.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingPlanServiceImpl implements TrainingPlanService {

    private final TrainingPlanRepository trainingPlanRepository;
    private final PlanCategoryRepository planCategoryRepository;
    private final TrainingPlanPreviewMapper trainingPlanPreviewMapper;
    private final TrainingPlanMapper trainingPlanMapper;


    public List<TrainingPlanPreviewDto> getAllForUser(Integer userId) {
        return trainingPlanRepository
                .findAllByUserId(userId).stream()
                    .sorted(byMostRecentlyModified())
                    .map(trainingPlanPreviewMapper::toDto)
                    .toList();
    }

    public TrainingPlanDto getById(Integer planId) {
        return trainingPlanRepository
                .findById(planId)
                .map(trainingPlanMapper::toDto)
                .orElseThrow();
    }

    @Override
    public TrainingPlanDto create(TrainingPlanDto plan, User user) {
        TrainingPlan toCreate = trainingPlanMapper.toEntity(plan);

        toCreate.setUser(user);

        TrainingPlan created = trainingPlanRepository.save(toCreate);
        return trainingPlanMapper.toDto(created);
    }

    @Override
    public TrainingPlanDto update(Integer planId, TrainingPlanDto planDto) {
        TrainingPlan trainingPlan = trainingPlanRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found"));

        PlanCategory planCategory = planCategoryRepository.findByNameIgnoreCase(planDto.getCategory())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        trainingPlan.setName(planDto.getName());
        trainingPlan.setDescription(planDto.getDescription());
        trainingPlan.setCategory(planCategory);
        trainingPlan.setDifficultyLvl(DifficultyLevel.fromStringIgnoreCase(planDto.getDifficultyLvl()));
        trainingPlan.setGoal(planDto.getGoal());

        TrainingPlan updated = trainingPlanRepository.save(trainingPlan);

        return trainingPlanMapper.toDto(updated);
    }

    @Override
    public void delete(Integer planId) {
        this.trainingPlanRepository.deleteById(planId);
    }

    private static Comparator<TrainingPlan> byMostRecentlyModified() {
        return Comparator.comparing((TrainingPlan plan) ->
                plan.getAuditInfo().getUpdatedAt() != null
                        ? plan.getAuditInfo().getUpdatedAt()
                        : plan.getAuditInfo().getCreatedAt()
        ).reversed();
    }
}
