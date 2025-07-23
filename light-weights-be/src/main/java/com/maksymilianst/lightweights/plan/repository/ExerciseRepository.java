package com.maksymilianst.lightweights.plan.repository;

import com.maksymilianst.lightweights.plan.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {

    Optional<Exercise> findByNameIgnoreCaseAndTrainingPlanId(String exerciseName, Integer trainingPlanId);

}
