package com.maksymilianst.lightweights.plan.service;

import com.maksymilianst.lightweights.plan.dto.TrainingExerciseDto;


public interface TrainingExerciseService {

    TrainingExerciseDto create(Integer trainingId, TrainingExerciseDto exerciseDto);

    TrainingExerciseDto update(Integer exerciseId, TrainingExerciseDto exerciseDto);

}