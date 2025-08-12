package com.maksymilianst.lightweights.publication.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TrainingPlanPublicationOpinionDto {
    private Integer id;
    private String content;
    private LocalDateTime creationTime;
    private String userEmail;
}
