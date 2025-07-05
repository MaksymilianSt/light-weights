package com.maksymilianst.lightweights.plan.repository;

import com.maksymilianst.lightweights.plan.model.TrainingPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrainingPlanRepository extends JpaRepository<TrainingPlan, Integer> {
    List<TrainingPlan> findAllByUserId(Integer userId);

    Optional<TrainingPlan> findByIdAndUserId(Integer planId, Integer userId);
}
