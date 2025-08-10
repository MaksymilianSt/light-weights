package com.maksymilianst.lightweights.publication.mapper;

import com.maksymilianst.lightweights.plan.model.Training;
import com.maksymilianst.lightweights.publication.dto.TrainingPlanPublicationDto;
import com.maksymilianst.lightweights.publication.dto.TrainingPublicationDto;
import com.maksymilianst.lightweights.publication.model.TrainingPlanPublication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = TrainingPublicationMapper.class)
public interface TrainingPlanPublicationMapper {

    @Mapping(source = "publicationDate", target = "publishedAt")
    @Mapping(source = "category.name", target = "category")
    @Mapping(source = "publication.author.email", target = "authorEmail")
    @Mapping(source = "publication", target = "trainings", qualifiedByName = "flattenAndMapTrainings")
    TrainingPlanPublicationDto toDto(TrainingPlanPublication publication);

    @Named("flattenAndMapTrainings")
    default List<TrainingPublicationDto> flattenAndMapTrainings(TrainingPlanPublication publication) {
        if (publication == null || publication.getBlocks() == null) {
            return List.of();
        }
        return publication.getBlocks().stream()
                .flatMap(block -> block.getTrainings().stream())
                .map(this::toTrainingDto)
                .collect(Collectors.toList());
    }

    @Mapping(source = "block.name", target = "blockName")
    TrainingPublicationDto toTrainingDto(Training training);
}
