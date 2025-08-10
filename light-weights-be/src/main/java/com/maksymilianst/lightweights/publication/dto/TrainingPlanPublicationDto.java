package com.maksymilianst.lightweights.publication.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TrainingPlanPublicationDto {
    private Integer id;
    @NotBlank
    private String name;
    private String description;
    @NotBlank
    private String category;
    @NotBlank
    private String difficultyLvl;
    private String goal;
    private LocalDateTime publishedAt;
    private String authorEmail;
    @Valid
    private List<TrainingPublicationDto> trainings;
}
