package com.maksymilianst.lightweights.plan.mapper;

import com.maksymilianst.lightweights.plan.dto.TrainingBlockDto;
import com.maksymilianst.lightweights.plan.model.TrainingBlock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = TrainingPreviewMapper.class)
public interface TrainingBlockMapper {

    TrainingBlockDto toDto(TrainingBlock block);

    @Mapping(target = "trainings", ignore = true)
    TrainingBlock toEntity(TrainingBlockDto block);

    default List<TrainingBlockDto> toDtos(Set<TrainingBlock> blocks) {
        return blocks.stream()
                .sorted(Comparator.comparing(TrainingBlock::getStart))
                .map(this::toDto)
                .toList();
    }
}
