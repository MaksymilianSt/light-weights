package com.maksymilianst.lightweights.plan.mapper;

import com.maksymilianst.lightweights.execution.model.TrainingExecution;
import com.maksymilianst.lightweights.plan.dto.TrainingPreviewDto;
import com.maksymilianst.lightweights.plan.model.Training;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TrainingPreviewMapper {

    @Mapping(source = "execution", target = "done", qualifiedByName = "isDone")
    TrainingPreviewDto toDto(Training training);

    @Named("isDone")
    default boolean isDone(TrainingExecution execution) {
        return execution != null && execution.isDone();
    }
}
