package com.maksymilianst.lightweights.execution.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TrainingSetExecutionDto {
    private Integer id;

    @NotNull
    private Integer repetitions;

    @DecimalMin(value = "0")
    @DecimalMax(value = "9999.99", message = "Weight must be at most 9999.99")
    private BigDecimal weight;

    private String tempo;

    @DecimalMin(value = "1.0", message = "Rpe must be at least 1")
    @DecimalMax(value = "10.0", message = "Rpe must be at most 10.0")
    private BigDecimal rpe;

    @NotNull
    @Min(0)
    private Integer sequence;

    private LocalDateTime executedAt;
}
