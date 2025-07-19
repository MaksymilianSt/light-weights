package com.maksymilianst.lightweights.plan.service.impl;

import com.maksymilianst.lightweights.plan.dto.TrainingBlockDto;
import com.maksymilianst.lightweights.plan.mapper.TrainingBlockMapper;
import com.maksymilianst.lightweights.plan.model.TrainingBlock;
import com.maksymilianst.lightweights.plan.model.TrainingPlan;
import com.maksymilianst.lightweights.plan.repository.TrainingBlockRepository;
import com.maksymilianst.lightweights.plan.repository.TrainingPlanRepository;
import com.maksymilianst.lightweights.plan.service.TrainingBlockService;
import com.maksymilianst.lightweights.plan.validator.TrainingBlockValidator;
import com.maksymilianst.lightweights.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainingBlockServiceImpl implements TrainingBlockService {

    private final TrainingBlockRepository trainingBlockRepository;
    private final TrainingPlanRepository trainingPlanRepository;
    private final TrainingBlockMapper trainingBlockMapper;
    private final TrainingBlockValidator trainingBlockValidator;


    @Override
    public TrainingBlockDto create(Integer planId, TrainingBlockDto block, User user) {
        trainingBlockValidator.validate(block);

        TrainingBlock toCreate = trainingBlockMapper.toEntity(block);

        TrainingPlan trainingPlan = trainingPlanRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found"));
        toCreate.setPlan(trainingPlan);

        TrainingBlock created = trainingBlockRepository.save(toCreate);
        return trainingBlockMapper.toDto(created);
    }

    @Override
    public TrainingBlockDto update(Integer blockId, TrainingBlockDto planDto) {
        trainingBlockValidator.validate(planDto);

        TrainingBlock toUpdate = trainingBlockRepository.findById(blockId)
                .orElseThrow(() -> new IllegalArgumentException("Block not found"));

        toUpdate.setName(planDto.getName());
        toUpdate.setDescription(planDto.getDescription());
        toUpdate.setStart(planDto.getStart());
        toUpdate.setEnd(planDto.getEnd());

        TrainingBlock updated = trainingBlockRepository.save(toUpdate);
        return trainingBlockMapper.toDto(updated);
    }

    @Override
    public void delete(Integer blockId) {
        this.trainingBlockRepository.deleteById(blockId);
    }

}
