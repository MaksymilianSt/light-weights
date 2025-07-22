package com.maksymilianst.lightweights.plan.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TrainingSetDto {
    private int id;
    private Integer sequence;
    @NotNull
    private Integer repetitions;
    @NotNull
    private BigDecimal weight;
    private String tempo;
}
