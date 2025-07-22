package com.maksymilianst.lightweights.plan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class TrainingBlockDto {
    private int id;
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private LocalDate start;
    @NotNull
    private LocalDate end;
    List<TrainingPreviewDto> trainings;
}
