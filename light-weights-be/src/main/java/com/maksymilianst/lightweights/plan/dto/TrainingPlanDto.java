package com.maksymilianst.lightweights.plan.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TrainingPlanDto {
    private Integer id;
    private String name;
    private String description;
    private String category;
    private String difficultyLvl;
    private String goal;
    private List<TrainingBlockDto> blocks;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
