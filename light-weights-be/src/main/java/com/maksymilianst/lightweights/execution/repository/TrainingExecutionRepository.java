package com.maksymilianst.lightweights.execution.repository;

import com.maksymilianst.lightweights.execution.model.TrainingExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingExecutionRepository extends JpaRepository<TrainingExecution, Integer> {
}
