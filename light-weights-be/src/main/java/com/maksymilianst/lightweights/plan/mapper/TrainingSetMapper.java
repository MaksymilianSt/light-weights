package com.maksymilianst.lightweights.plan.mapper;

import com.maksymilianst.lightweights.plan.dto.TrainingSetDto;
import com.maksymilianst.lightweights.plan.model.TrainingSet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Comparator;
import java.util.List;
import java.util.Set;


@Mapper(componentModel = "spring")
public interface TrainingSetMapper {

    TrainingSetDto toDto(TrainingSet set);

    @Mapping(target = "id", ignore = true)
    TrainingSet toEntity(TrainingSetDto setDto);

    default List<TrainingSetDto> toDtos(Set<TrainingSet> sets) {
        return sets.stream()
                .sorted(Comparator.comparing(TrainingSet::getSequence))
                .map(this::toDto)
                .toList();
    }
}
