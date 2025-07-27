package com.maksymilianst.lightweights.execution.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class TrainingExecutionDto {
    private Integer id;
    private String notes;
    private LocalDate realizationDate;
    private Boolean done;

    private List<TrainingExerciseExecutionDto> trainingExerciseExecutions;
}
