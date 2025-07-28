package com.maksymilianst.lightweights.execution.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TrainingExecutionDto {
    private Integer id;
    private String notes;
    private LocalDateTime startDate;
    private LocalDateTime finishDate;

    private List<TrainingExerciseExecutionDto> trainingExerciseExecutions;
}
