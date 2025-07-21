package com.maksymilianst.lightweights.plan.dto;

import lombok.Builder;
import lombok.Data;


import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class TrainingDto {
    private Integer id;
    private String name;
    private String description;
    private LocalDate date;
    private Integer executionId;
    private String blockName;

    private List<TrainingExerciseDto> exercises;

}
