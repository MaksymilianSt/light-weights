package com.maksymilianst.lightweights.plan.repository;

import com.maksymilianst.lightweights.plan.model.Exercise;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {

    Optional<Exercise> findByNameIgnoreCaseAndTrainingPlanId(String exerciseName, Integer trainingPlanId);

    @Modifying
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Query("""
              DELETE FROM Exercise e
              WHERE e.trainingPlan.id = :trainingPlanId
                AND NOT EXISTS (
                  SELECT te.id
                  FROM TrainingExercise te
                  WHERE te.exercise.id = e.id
                )
            """)
    void cleanUpUnusedExercisesInTrainingPlan(Integer trainingPlanId);

}
