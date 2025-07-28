package com.maksymilianst.lightweights.execution.service.impl;

import com.maksymilianst.lightweights.execution.dto.TrainingExecutionDto;
import com.maksymilianst.lightweights.execution.exception.ExecutionAlreadyExistsException;
import com.maksymilianst.lightweights.execution.exception.ExecutionFinishedException;
import com.maksymilianst.lightweights.execution.exception.TrainingExecutionException;
import com.maksymilianst.lightweights.execution.mapper.SetExecutionMapper;
import com.maksymilianst.lightweights.execution.mapper.TrainingExecutionMapper;
import com.maksymilianst.lightweights.execution.model.TrainingExecution;
import com.maksymilianst.lightweights.execution.model.TrainingExerciseExecution;
import com.maksymilianst.lightweights.execution.model.TrainingSetExecution;
import com.maksymilianst.lightweights.execution.repository.TrainingExecutionRepository;
import com.maksymilianst.lightweights.execution.service.TrainingExecutionService;
import com.maksymilianst.lightweights.plan.model.Training;
import com.maksymilianst.lightweights.plan.model.TrainingExercise;
import com.maksymilianst.lightweights.plan.model.TrainingSet;
import com.maksymilianst.lightweights.plan.repository.TrainingRepository;
import com.maksymilianst.lightweights.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainingExecutionServiceImpl implements TrainingExecutionService {
    private final static BigDecimal DEFAULT_RPE = BigDecimal.valueOf(5);

    private final TrainingExecutionRepository trainingExecutionRepository;
    private final TrainingRepository trainingRepository;
    private final TrainingExecutionMapper trainingExecutionMapper;
    private final SetExecutionMapper setExecutionMapper;

    @Override
    public TrainingExecutionDto finish(Integer trainingExecutionId) {
        TrainingExecution execution = trainingExecutionRepository.findById(trainingExecutionId)
                .orElseThrow(() -> new TrainingExecutionException("Invalid training execution id"));

        if (execution.getFinishDate() != null) {
            throw new ExecutionFinishedException();
        }
        execution.setFinishDate(LocalDateTime.now());

        TrainingExecution saved = trainingExecutionRepository.save(execution);
        return trainingExecutionMapper.toDto(saved);

    }

    @Override
    public TrainingExecutionDto update(Integer trainingExecutionId, TrainingExecutionDto trainingExecutionDto) {
        TrainingExecution toUpdate = trainingExecutionRepository.findById(trainingExecutionId)
                .orElseThrow(() -> new TrainingExecutionException("Invalid training execution id"));

        if (toUpdate.getFinishDate() != null) {
            throw new ExecutionFinishedException();
        }

        toUpdate.setNotes(trainingExecutionDto.getNotes());

        TrainingExecution updated = trainingExecutionRepository.save(toUpdate);
        return trainingExecutionMapper.toDto(updated);
    }

    @Override
    @Transactional
    public TrainingExecutionDto create(Integer trainingId, User user) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new TrainingExecutionException("Invalid training id"));

        if (training.getExecution() != null) {
            throw new ExecutionAlreadyExistsException();
        }

        TrainingExecution newExecution = new TrainingExecution();

        newExecution.setUser(user);
        newExecution.setReferencedTraining(training);
        training.setExecution(newExecution);
        newExecution.setTrainingExerciseExecutions(
                createExerciseExecutions(training.getExercises(), newExecution)
        );

        TrainingExecution created = trainingExecutionRepository.save(newExecution);
        return trainingExecutionMapper.toDto(created);
    }

    private Set<TrainingExerciseExecution> createExerciseExecutions(Set<TrainingExercise> exercises, TrainingExecution execution) {
        return exercises.stream()
                .map(exercise -> {
                    var newExerciseExecution = new TrainingExerciseExecution();
                    newExerciseExecution.setTrainingExecution(execution);

                    newExerciseExecution.setReferencedExercise(exercise);
                    newExerciseExecution.setExercise(exercise.getExercise());
                    newExerciseExecution.setSequence(exercise.getSequence());
                    newExerciseExecution.setTrainingSetExecutions(
                            createSetExecutions(exercise.getSets(), newExerciseExecution)
                    );

                    return newExerciseExecution;
                })
                .collect(Collectors.toSet());
    }

    private Set<TrainingSetExecution> createSetExecutions(Set<TrainingSet> sets, TrainingExerciseExecution exerciseExecution) {
        return sets.stream()
                .map(set -> {
                    var newSetExecution = setExecutionMapper.toNewEntity(set);

                    newSetExecution.setExerciseExecution(exerciseExecution);
                    newSetExecution.setRpe(DEFAULT_RPE);

                    return newSetExecution;
                })
                .collect(Collectors.toSet());
    }

}
