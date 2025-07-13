package com.maksymilianst.lightweights.plan.repository;

import com.maksymilianst.lightweights.plan.model.PlanCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanCategoryRepository extends JpaRepository<PlanCategory, Integer> {
    Optional<PlanCategory> findByNameIgnoreCase(String name);
}
