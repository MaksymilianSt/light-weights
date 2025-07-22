package com.maksymilianst.lightweights.plan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class TrainingDto {
    private Integer id;
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private LocalDate date;
    private Integer executionId;
    private String blockName;

    private List<TrainingExerciseDto> exercises;

}
