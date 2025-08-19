package com.maksymilianst.lightweights.publication.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TrainingPlanPublicationOpinionDto {
    private Integer id;
    @NotBlank
    private String content;
    private LocalDateTime creationTime;
    private String userEmail;
}
