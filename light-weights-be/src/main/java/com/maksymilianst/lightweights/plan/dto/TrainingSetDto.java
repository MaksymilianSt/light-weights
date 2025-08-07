package com.maksymilianst.lightweights.plan.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;


import java.math.BigDecimal;

@Data
@Builder
public class TrainingSetDto {
    private Integer id;
    private Integer sequence;
    @NotNull
    private Integer repetitions;
    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "9999.99", message = "Weight must be at most 9999.99")
    private BigDecimal weight;
    @Size(max = 7, message = "Tempo cannot be longer than 7 chars")
    private String tempo;
}
