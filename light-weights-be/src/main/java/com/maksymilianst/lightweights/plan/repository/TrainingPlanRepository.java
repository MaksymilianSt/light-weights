package com.maksymilianst.lightweights.plan.repository;

import com.maksymilianst.lightweights.plan.model.TrainingPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingPlanRepository extends JpaRepository<TrainingPlan, Integer> {
    List<TrainingPlan> findAllByUserId(Integer userId);

    boolean existsByIdAndUserId(Integer id, Integer userId);
}
