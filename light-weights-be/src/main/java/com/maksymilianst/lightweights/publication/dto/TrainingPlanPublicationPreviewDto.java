package com.maksymilianst.lightweights.publication.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class TrainingPlanPublicationPreviewDto {
    private Integer id;
    private String name;
    private String category;
    private String difficultyLvl;
    private String goal;
    private LocalDateTime publishedAt;
    private String authorEmail;
    private Integer downloads;

    private LocalDate start;
    private LocalDate end;
}
