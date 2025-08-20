package com.maksymilianst.lightweights.plan.service.impl;

import com.maksymilianst.lightweights.plan.dto.TrainingDto;
import com.maksymilianst.lightweights.plan.mapper.TrainingMapper;
import com.maksymilianst.lightweights.plan.model.Training;
import com.maksymilianst.lightweights.plan.model.TrainingBlock;
import com.maksymilianst.lightweights.plan.repository.TrainingBlockRepository;
import com.maksymilianst.lightweights.plan.repository.TrainingRepository;
import com.maksymilianst.lightweights.plan.service.TrainingService;
import com.maksymilianst.lightweights.plan.validator.TrainingValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingBlockRepository trainingBlockRepository;
    private final TrainingMapper trainingMapper;
    private final TrainingValidator trainingValidator;

    @Override
    public TrainingDto getById(Integer trainingId) {
        return trainingRepository
                .findById(trainingId)
                .map(trainingMapper::toDto)
                .orElseThrow();
    }

    @Override
    public TrainingDto create(Integer blockId, TrainingDto trainingDto) {
        TrainingBlock block = trainingBlockRepository.findById(blockId)
                .orElseThrow(() -> new IllegalArgumentException("Block not found"));
        Training toCreate = trainingMapper.toEntity(trainingDto);

        trainingValidator.validate(trainingDto, block);

        toCreate.setBlock(block);

        Training created = trainingRepository.save(toCreate);
        return trainingMapper.toDto(created);
    }

    @Override
    public TrainingDto update(Integer trainingId, TrainingDto trainingDto) {
        Training toUpdate = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new IllegalArgumentException("Training not found"));

        trainingValidator.validate(trainingDto, toUpdate.getBlock());

        toUpdate.setName(trainingDto.getName());
        toUpdate.setDescription(trainingDto.getDescription());
        toUpdate.setDate(trainingDto.getDate());

        Training updated = trainingRepository.save(toUpdate);
        return trainingMapper.toDto(updated);
    }

    @Override
    public void delete(Integer trainingId) {
        trainingRepository.deleteById(trainingId);
    }
}
