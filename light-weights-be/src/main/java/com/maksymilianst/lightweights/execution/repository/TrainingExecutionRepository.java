package com.maksymilianst.lightweights.execution.repository;

import com.maksymilianst.lightweights.execution.model.TrainingExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingExecutionRepository extends JpaRepository<TrainingExecution, Integer> {
    List<TrainingExecution> findAllByUserId(Integer userId);
}
