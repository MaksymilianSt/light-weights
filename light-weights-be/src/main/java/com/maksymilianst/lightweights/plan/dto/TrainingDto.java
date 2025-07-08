package com.maksymilianst.lightweights.plan.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TrainingDto {
    private int id;
    private String name;
    private String description;
    private LocalDateTime date;
    private Integer executionId;
    private String blockName;

    private List<TrainingExerciseDto> exercises;
}
