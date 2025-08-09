package com.maksymilianst.lightweights.publication.dto;

import com.maksymilianst.lightweights.plan.dto.TrainingExerciseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class TrainingPublicationDto {
    private Integer id;
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private LocalDate date;
    private String blockName;

    @Valid
    private List<TrainingExerciseDto> exercises;
}
