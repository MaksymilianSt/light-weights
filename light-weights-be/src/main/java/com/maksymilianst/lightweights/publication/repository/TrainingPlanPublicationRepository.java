package com.maksymilianst.lightweights.publication.repository;

import com.maksymilianst.lightweights.publication.model.TrainingPlanPublication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingPlanPublicationRepository extends JpaRepository<TrainingPlanPublication, Integer> {

    Boolean existsByIdAndAuthorId(Integer id, Integer authorId);
}
