package com.maksymilianst.lightweights.plan.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TrainingSetDto {
    private int id;
    private Integer sequence;
    private Integer repetitions;
    private BigDecimal weight;
    private String tempo;
}
