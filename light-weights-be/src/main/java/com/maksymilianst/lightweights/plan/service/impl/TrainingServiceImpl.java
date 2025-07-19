package com.maksymilianst.lightweights.plan.service.impl;

import com.maksymilianst.lightweights.plan.dto.TrainingDto;
import com.maksymilianst.lightweights.plan.mapper.TrainingMapper;
import com.maksymilianst.lightweights.plan.repository.TrainingRepository;
import com.maksymilianst.lightweights.plan.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;

    @Override
    public TrainingDto getById(Integer trainingId) {
        return trainingRepository
                .findById(trainingId)
                .map(trainingMapper::toDto)
                .orElseThrow();
    }
}
