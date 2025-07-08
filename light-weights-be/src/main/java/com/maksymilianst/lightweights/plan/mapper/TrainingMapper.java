package com.maksymilianst.lightweights.plan.mapper;

import com.maksymilianst.lightweights.plan.dto.TrainingDto;
import com.maksymilianst.lightweights.plan.model.Training;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = TrainingExerciseMapper.class)
public interface TrainingMapper {

    @Mapping(source = "block.name", target = "blockName")
    TrainingDto toDto(Training training);

}
