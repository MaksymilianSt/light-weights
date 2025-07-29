package com.maksymilianst.lightweights.plan.mapper;

import com.maksymilianst.lightweights.execution.model.TrainingExecution;
import com.maksymilianst.lightweights.plan.dto.TrainingPreviewDto;
import com.maksymilianst.lightweights.plan.model.Training;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface TrainingPreviewMapper {

    @Mapping(source = "execution", target = "done", qualifiedByName = "isDone")
    @Mapping(source = "execution.id", target = "executionId")
    TrainingPreviewDto toDto(Training training);

    @Named("isDone")
    default boolean isDone(TrainingExecution execution) {
        return execution != null && execution.getFinishDate() != null;
    }

    default List<TrainingPreviewDto> toDtos(Set<Training> trainings) {
        return trainings.stream()
                .sorted(Comparator.comparing(Training::getDate))
                .map(this::toDto)
                .toList();
    }
}
