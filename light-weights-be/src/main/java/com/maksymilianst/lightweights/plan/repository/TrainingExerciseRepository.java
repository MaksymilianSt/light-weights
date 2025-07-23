package com.maksymilianst.lightweights.plan.repository;

import com.maksymilianst.lightweights.plan.model.TrainingExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingExerciseRepository extends JpaRepository<TrainingExercise, Integer> {

}
