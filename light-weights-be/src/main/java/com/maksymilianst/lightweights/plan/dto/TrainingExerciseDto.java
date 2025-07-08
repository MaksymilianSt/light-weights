package com.maksymilianst.lightweights.plan.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TrainingExerciseDto {
    private int id;
    private String name;
    private String description;
    private Integer sequence;

    private List<TrainingSetDto> sets;
}
