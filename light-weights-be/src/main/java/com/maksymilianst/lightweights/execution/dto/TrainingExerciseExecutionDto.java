package com.maksymilianst.lightweights.execution.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TrainingExerciseExecutionDto {
    private Integer id;
    private String name;
    private String notes;
    private Boolean done;

    @Min(0)
    @NotNull
    private Integer sequence;

    private List<TrainingSetExecutionDto> trainingSetExecutions;
}
