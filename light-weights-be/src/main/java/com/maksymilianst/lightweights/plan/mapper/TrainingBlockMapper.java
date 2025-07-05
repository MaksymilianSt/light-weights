package com.maksymilianst.lightweights.plan.mapper;

import com.maksymilianst.lightweights.plan.dto.TrainingBlockDto;
import com.maksymilianst.lightweights.plan.model.TrainingBlock;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = TrainingPreviewMapper.class)
public interface TrainingBlockMapper {

    TrainingBlockDto toDto(TrainingBlock block);

}
