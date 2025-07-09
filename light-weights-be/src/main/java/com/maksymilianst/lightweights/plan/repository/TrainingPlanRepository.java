package com.maksymilianst.lightweights.plan.repository;

import com.maksymilianst.lightweights.plan.model.TrainingPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingPlanRepository extends JpaRepository<TrainingPlan, Integer> {
    List<TrainingPlan> findAllByUserId(Integer userId);

    boolean existsByIdAndUserId(Integer id, Integer userId);
}
