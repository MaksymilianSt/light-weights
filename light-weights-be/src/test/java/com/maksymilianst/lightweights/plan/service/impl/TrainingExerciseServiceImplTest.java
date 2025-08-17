package com.maksymilianst.lightweights.plan.service.impl;

import com.maksymilianst.lightweights.plan.dto.TrainingExerciseDto;
import com.maksymilianst.lightweights.plan.listener.event.TrainingExerciseChangedEvent;
import com.maksymilianst.lightweights.plan.mapper.TrainingExerciseMapper;
import com.maksymilianst.lightweights.plan.model.*;
import com.maksymilianst.lightweights.plan.repository.ExerciseRepository;
import com.maksymilianst.lightweights.plan.repository.TrainingExerciseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingExerciseServiceImplTest {

    @Mock
    private TrainingExerciseRepository trainingExerciseRepository;
    @Mock
    private TrainingExerciseMapper trainingExerciseMapper;
    @Mock
    private ExerciseRepository exerciseRepository;
    @Mock
    private ApplicationEventPublisher publisher;

    @InjectMocks
    private TrainingExerciseServiceImpl service;

    private final Integer PLAN_ID = 1;
    private final TrainingPlan plan = new TrainingPlan();
    private final TrainingBlock block = new TrainingBlock();
    private final Training training = new Training();
    private final Integer TRAINING_EXERCISE_ID = 1;
    private final TrainingExercise trainingExercise = new TrainingExercise();


    @BeforeEach
    void setUp() {
        plan.setId(PLAN_ID);
        block.setPlan(plan);
        training.setBlock(block);
        trainingExercise.setId(TRAINING_EXERCISE_ID);
        trainingExercise.setTraining(training);
    }

    @Test
    void update_shouldFireTrainingExerciseChangedEvent() {
        // given
        final var dto = TrainingExerciseDto.builder()
                .sets(List.of())
                .build();

        when(trainingExerciseRepository.findById(TRAINING_EXERCISE_ID))
                .thenReturn(Optional.of(trainingExercise));
        when(trainingExerciseRepository.save(any()))
                .thenReturn(trainingExercise);
        when(trainingExerciseMapper.toDto(any()))
                .thenReturn(dto);

        // when
        service.update(TRAINING_EXERCISE_ID, dto);

        // then
        ArgumentCaptor<TrainingExerciseChangedEvent> captor = ArgumentCaptor.forClass(TrainingExerciseChangedEvent.class);
        verify(publisher).publishEvent(captor.capture());
        TrainingExerciseChangedEvent publishedEvent = captor.getValue();

        assertInstanceOf(TrainingExerciseChangedEvent.class, publishedEvent, "Event should be of type TrainingExerciseChangedEvent");
        assertEquals(PLAN_ID, publishedEvent.planId());
    }

    @Test
    void shouldReturnExistingExercise_whenExerciseExistsInPlan() {
        // given
        final String exerciseName = "squat";
        final Exercise existingExercise = new Exercise();
        existingExercise.setName(exerciseName);
        existingExercise.setTrainingPlan(plan);

        var dto = TrainingExerciseDto.builder()
                .id(TRAINING_EXERCISE_ID)
                .name(exerciseName)
                .sets(List.of())
                .build();

        when(trainingExerciseRepository.findById(TRAINING_EXERCISE_ID))
                .thenReturn(Optional.of(trainingExercise));
        when(exerciseRepository.findByNameIgnoreCaseAndTrainingPlanId(exerciseName, plan.getId()))
                .thenReturn(Optional.of(existingExercise));

        // when
        service.update(trainingExercise.getId(), dto);

        // then
        verify(exerciseRepository, never()).save(any());
    }
}