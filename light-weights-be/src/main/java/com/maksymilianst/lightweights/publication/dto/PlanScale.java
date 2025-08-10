package com.maksymilianst.lightweights.publication.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PlanScale {
    private Double weightScale;
    private LocalDate planStartDate;
    private Integer daysOffSet;
}
