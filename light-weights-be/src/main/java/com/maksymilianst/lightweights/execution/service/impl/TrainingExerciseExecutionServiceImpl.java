package com.maksymilianst.lightweights.execution.service.impl;

import com.maksymilianst.lightweights.execution.dto.TrainingExerciseExecutionDto;
import com.maksymilianst.lightweights.execution.dto.TrainingSetExecutionDto;
import com.maksymilianst.lightweights.execution.exception.ExecutionFinishedException;
import com.maksymilianst.lightweights.execution.exception.TrainingExecutionException;
import com.maksymilianst.lightweights.execution.mapper.ExerciseExecutionMapper;
import com.maksymilianst.lightweights.execution.model.TrainingExerciseExecution;
import com.maksymilianst.lightweights.execution.model.TrainingSetExecution;
import com.maksymilianst.lightweights.execution.repository.TrainingExerciseExecutionRepository;
import com.maksymilianst.lightweights.execution.service.TrainingExerciseExecutionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
class TrainingExerciseExecutionServiceImpl implements TrainingExerciseExecutionService {

    private final TrainingExerciseExecutionRepository exerciseExecutionRepository;
    private final TrainingExerciseExecutionRepository trainingExerciseExecutionRepository;
    private final ExerciseExecutionMapper exerciseExecutionMapper;

    @Override
    @Transactional
    public TrainingExerciseExecutionDto update(Integer exerciseExecutionId, TrainingExerciseExecutionDto exerciseExecutionDto) {
        TrainingExerciseExecution toUpdate = exerciseExecutionRepository.findById(exerciseExecutionId)
                .orElseThrow(() -> new TrainingExecutionException("Invalid exercise execution id"));

        if(toUpdate.getTrainingExecution().getFinishDate() != null){
            log.info("Attempt to update already finished training execution with id: {}", exerciseExecutionId);
            throw new ExecutionFinishedException();
        }
        toUpdate.setNotes(exerciseExecutionDto.getNotes());
        toUpdate.setSequence(exerciseExecutionDto.getSequence());
        toUpdate.setDone(exerciseExecutionDto.getDone());

        updateSets(toUpdate, exerciseExecutionDto);

        TrainingExerciseExecution updated = trainingExerciseExecutionRepository.save(toUpdate);
        return exerciseExecutionMapper.toDto(updated);
    }

    private void updateSets(TrainingExerciseExecution exerciseExecution, TrainingExerciseExecutionDto exerciseExecutionDto) {
        Map<Integer, TrainingSetExecution> currentSets = exerciseExecution.getTrainingSetExecutions().stream()
                .collect(Collectors.toMap(
                        TrainingSetExecution::getId,
                        Function.identity()
                ));

        exerciseExecutionDto.getTrainingSetExecutions()
                .forEach(setDto -> {
                    Optional.ofNullable(currentSets.get(setDto.getId()))
                            .ifPresent(setToUpdate -> updateSet(setToUpdate, setDto));
                });
    }

    private static void updateSet(TrainingSetExecution setToUpdate, TrainingSetExecutionDto source) {
        setToUpdate.setSequence(source.getSequence());
        setToUpdate.setWeight(source.getWeight());
        setToUpdate.setTempo(source.getTempo());
        setToUpdate.setRpe(source.getRpe());
        setToUpdate.setSequence(source.getSequence());
        setToUpdate.setExecutedAt(source.getExecutedAt());
    }

}
