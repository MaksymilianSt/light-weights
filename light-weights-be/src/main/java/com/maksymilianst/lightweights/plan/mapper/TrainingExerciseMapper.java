package com.maksymilianst.lightweights.plan.mapper;

import com.maksymilianst.lightweights.plan.dto.TrainingExerciseDto;
import com.maksymilianst.lightweights.plan.model.TrainingExercise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Comparator;
import java.util.List;
import java.util.Set;


@Mapper(componentModel = "spring", uses = TrainingSetMapper.class)
public interface TrainingExerciseMapper {

    @Mapping(source = "exercise.name", target = "name")
    TrainingExerciseDto toDto(TrainingExercise exercise);

    default List<TrainingExerciseDto> toDtos(Set<TrainingExercise> exercises) {
        return exercises.stream()
                .sorted(Comparator.comparing(TrainingExercise::getSequence))
                .map(this::toDto)
                .toList();
    }
}
