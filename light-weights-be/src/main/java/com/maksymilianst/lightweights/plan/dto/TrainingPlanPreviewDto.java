package com.maksymilianst.lightweights.plan.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TrainingPlanPreviewDto {
    private int id;
    private String name;
    private String description;
    private String category;
    private String difficultyLvl;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
