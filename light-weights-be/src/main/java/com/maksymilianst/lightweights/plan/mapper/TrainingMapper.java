package com.maksymilianst.lightweights.plan.mapper;

import com.maksymilianst.lightweights.plan.dto.TrainingDto;
import com.maksymilianst.lightweights.plan.model.Training;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = TrainingExerciseMapper.class)
public interface TrainingMapper {

    @Mapping(source = "block.name", target = "blockName")
    @Mapping(
            target = "executionId",
            expression = "java(training.getExecution() != null ? training.getExecution().getId() : null)"
    )
    TrainingDto toDto(Training training);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "exercises", ignore = true)
    Training toEntity(TrainingDto trainingDto);

}
