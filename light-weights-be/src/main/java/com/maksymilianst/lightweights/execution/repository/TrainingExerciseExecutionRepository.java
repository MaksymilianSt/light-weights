package com.maksymilianst.lightweights.execution.repository;

import com.maksymilianst.lightweights.execution.model.TrainingExerciseExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingExerciseExecutionRepository extends JpaRepository<TrainingExerciseExecution, Integer> {
}
