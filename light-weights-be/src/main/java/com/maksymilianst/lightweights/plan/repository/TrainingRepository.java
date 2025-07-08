package com.maksymilianst.lightweights.plan.repository;

import com.maksymilianst.lightweights.plan.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TrainingRepository extends JpaRepository<Training, Integer> {

}
