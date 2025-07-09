package com.maksymilianst.lightweights.plan.repository;

import com.maksymilianst.lightweights.plan.model.TrainingBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface TrainingBlockRepository extends JpaRepository<TrainingBlock, Integer> {

    @Query("""
                SELECT COUNT(plan) > 0
                FROM TrainingPlan plan
                JOIN plan.blocks block
                WHERE block.id = :blockId
                  AND plan.user.id = :userId
            """)
    boolean existsByBlockIdAndUserId(@Param("blockId") Integer blockId, @Param("userId") Integer userId);
}
