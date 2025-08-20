package com.maksymilianst.lightweights.plan.listener;

import com.maksymilianst.lightweights.plan.listener.event.TrainingExerciseChangedEvent;
import com.maksymilianst.lightweights.plan.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component()
@RequiredArgsConstructor
class TrainingExerciseCleanupListener {
    private final ExerciseRepository exerciseRepository;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onTrainingExerciseUpdated(TrainingExerciseChangedEvent event) {
            exerciseRepository.cleanUpUnusedExercisesInTrainingPlan(event.planId());
    }
}
