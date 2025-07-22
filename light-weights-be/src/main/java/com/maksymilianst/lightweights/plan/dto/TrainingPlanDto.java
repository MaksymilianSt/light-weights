package com.maksymilianst.lightweights.plan.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TrainingPlanDto {
    private Integer id;
    @NotBlank
    private String name;
    private String description;
    @NotBlank
    private String category;
    @NotBlank
    private String difficultyLvl;
    private String goal;
    private List<TrainingBlockDto> blocks;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
