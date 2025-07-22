package com.maksymilianst.lightweights.plan.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TrainingExerciseDto {
    private int id;
    @NotNull
    private String name;
    private String description;
    private Integer sequence;

    private List<TrainingSetDto> sets;
}
