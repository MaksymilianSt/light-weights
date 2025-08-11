package com.maksymilianst.lightweights.publication.repository;

import com.maksymilianst.lightweights.publication.model.TrainingPlanPublicationDownload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingPlanPublicationDownloadRepository extends JpaRepository<TrainingPlanPublicationDownload, Integer> {
}
