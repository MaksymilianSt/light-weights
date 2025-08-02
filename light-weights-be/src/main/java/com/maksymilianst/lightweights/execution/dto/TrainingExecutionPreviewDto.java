package com.maksymilianst.lightweights.execution.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TrainingExecutionPreviewDto {
    private Integer id;
    private Boolean finished;
    private LocalDateTime startDate;
    private String planName;
    private String trainingName;
    private Double completionPercentage;
}
