package com.maksymilianst.lightweights.publication.repository;

import com.maksymilianst.lightweights.publication.model.TrainingPlanPublicationOpinion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingPlanPublicationOpinionRepository extends JpaRepository<TrainingPlanPublicationOpinion, Integer> {
}
