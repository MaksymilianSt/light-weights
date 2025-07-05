package com.maksymilianst.lightweights.plan.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class TrainingBlockDto {
    private int id;
    private String name;
    private String description;
    private LocalDate start;
    private LocalDate end;
    List<TrainingPreviewDto> trainings;
}
