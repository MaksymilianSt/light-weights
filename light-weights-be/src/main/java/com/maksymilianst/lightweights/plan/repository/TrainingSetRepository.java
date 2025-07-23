package com.maksymilianst.lightweights.plan.repository;

import com.maksymilianst.lightweights.plan.model.TrainingSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingSetRepository extends JpaRepository<TrainingSet, Integer> {

}
