package com.maksymilianst.lightweights.plan.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TrainingExerciseDto {
    private Integer id;
    @NotBlank
    private String name;
    private String description;
    @NotNull
    @Min(0)
    private Integer sequence;

    private List<TrainingSetDto> sets;
}
