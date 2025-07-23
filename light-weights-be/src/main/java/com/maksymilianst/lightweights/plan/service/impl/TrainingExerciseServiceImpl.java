package com.maksymilianst.lightweights.plan.service.impl;

import com.maksymilianst.lightweights.plan.dto.TrainingExerciseDto;
import com.maksymilianst.lightweights.plan.mapper.TrainingExerciseMapper;
import com.maksymilianst.lightweights.plan.mapper.TrainingSetMapper;
import com.maksymilianst.lightweights.plan.model.*;
import com.maksymilianst.lightweights.plan.repository.ExerciseRepository;
import com.maksymilianst.lightweights.plan.repository.TrainingExerciseRepository;
import com.maksymilianst.lightweights.plan.repository.TrainingRepository;
import com.maksymilianst.lightweights.plan.service.TrainingExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainingExerciseServiceImpl implements TrainingExerciseService {

    private final TrainingRepository trainingRepository;
    private final TrainingExerciseRepository trainingExerciseRepository;
    private final TrainingExerciseMapper trainingExerciseMapper;
    private final ExerciseRepository exerciseRepository;
    private final TrainingSetMapper setMapper;


    @Override
    public TrainingExerciseDto create(Integer trainingId, TrainingExerciseDto trainingExerciseDto) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new IllegalArgumentException("Training not found"));

        TrainingExercise toCreate = new TrainingExercise();

        updateTrainingExerciseDetails(toCreate, trainingExerciseDto, training);
        toCreate.setTraining(training);
        toCreate.setSets(
                trainingExerciseDto.getSets().stream()
                        .map(setMapper::toEntity)
                        .peek(newSet -> newSet.setExercise(toCreate))
                        .collect(Collectors.toSet())
        );

        TrainingExercise created = trainingExerciseRepository.save(toCreate);
        return trainingExerciseMapper.toDto(created);
    }

    @Override
    public TrainingExerciseDto update(Integer exerciseId, TrainingExerciseDto trainingExerciseDto) {
        TrainingExercise toUpdate = trainingExerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new IllegalArgumentException("TrainingExercise not found"));

        updateTrainingExerciseDetails(toUpdate, trainingExerciseDto, toUpdate.getTraining());
        updateOrCreateTrainingSets(trainingExerciseDto, toUpdate);


        TrainingExercise updated = trainingExerciseRepository.save(toUpdate);
        return trainingExerciseMapper.toDto(updated);
    }

    private void updateTrainingExerciseDetails(TrainingExercise toUpdate, TrainingExerciseDto trainingExerciseDto, Training toUpdate1) {
        toUpdate.setExercise(
                findExerciseByNameAndPlanIdOrCreate(trainingExerciseDto.getName(), toUpdate1.getBlock().getPlan())
        );
        toUpdate.setSequence(trainingExerciseDto.getSequence());
        toUpdate.setDescription(trainingExerciseDto.getDescription());
    }

    private static void updateOrCreateTrainingSets(TrainingExerciseDto trainingExerciseDto, TrainingExercise trainingExercise) {
        Map<Integer, TrainingSet> currentSets = trainingExercise.getSets().stream()
                .collect(Collectors.toMap(
                        TrainingSet::getId,
                        Function.identity()
                ));

        Set<TrainingSet> updatedSets = trainingExerciseDto.getSets().stream()
                .map(setDto -> {
                    TrainingSet setToSave = currentSets.getOrDefault(
                            setDto.getId(),
                            createNewTrainingSet(trainingExercise)
                    );
                    setToSave.setSequence(setDto.getSequence());
                    setToSave.setRepetitions(setDto.getRepetitions());
                    setToSave.setWeight(setDto.getWeight());
                    setToSave.setTempo(setDto.getTempo());

                    return setToSave;
                })
                .collect(Collectors.toSet());

        trainingExercise.getSets().clear();
        trainingExercise.getSets().addAll(updatedSets);
    }

    private static TrainingSet createNewTrainingSet(TrainingExercise toUpdate) {
        var newSet = new TrainingSet();

        newSet.setId(null);
        newSet.setExercise(toUpdate);

        return newSet;
    }

    private Exercise findExerciseByNameAndPlanIdOrCreate(String exerciseName, TrainingPlan plan) {
        return exerciseRepository
                .findByNameIgnoreCaseAndTrainingPlanId(exerciseName, plan.getId())
                .orElseGet(() -> {
                    Exercise newExercise = new Exercise();

                    newExercise.setName(exerciseName);
                    newExercise.setTrainingPlan(plan);

                    return exerciseRepository.save(newExercise);
                });
    }

}
