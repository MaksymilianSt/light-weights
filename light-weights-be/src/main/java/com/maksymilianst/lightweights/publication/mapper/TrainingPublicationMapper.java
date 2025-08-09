package com.maksymilianst.lightweights.publication.mapper;

import com.maksymilianst.lightweights.plan.mapper.TrainingExerciseMapper;
import com.maksymilianst.lightweights.plan.model.Training;
import com.maksymilianst.lightweights.publication.dto.TrainingPublicationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TrainingExerciseMapper.class)
public interface TrainingPublicationMapper {

    @Mapping(source = "block.name", target = "blockName")
    TrainingPublicationDto toDto(Training training);
}
