package com.maksymilianst.lightweights.plan.repository;

import com.maksymilianst.lightweights.plan.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Integer> {

    @Query("""
                SELECT COUNT(plan) > 0
                FROM TrainingPlan plan
                JOIN plan.blocks block
                JOIN block.trainings training
                WHERE training.id = :trainingId
                  AND plan.user.id = :userId
            """)
    boolean existsByTrainingIdAndUserId(@Param("trainingId") Integer trainingId, @Param("userId") Integer userId);
}
