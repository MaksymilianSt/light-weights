package com.maksymilianst.lightweights.plan.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TrainingPreviewDto {
    private int id;
    private String name;
    private String description;
    private LocalDateTime date;
    private boolean done;
}
