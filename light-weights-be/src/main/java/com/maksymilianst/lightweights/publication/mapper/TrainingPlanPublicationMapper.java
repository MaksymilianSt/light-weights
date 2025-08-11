package com.maksymilianst.lightweights.publication.mapper;

import com.maksymilianst.lightweights.publication.dto.TrainingPlanPublicationDto;
import com.maksymilianst.lightweights.publication.dto.TrainingPublicationDto;
import com.maksymilianst.lightweights.publication.model.TrainingPlanPublication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = TrainingPublicationMapper.class)
public abstract class TrainingPlanPublicationMapper {

    @Autowired
    private TrainingPublicationMapper trainingPublicationMapper;

    @Mapping(source = "publicationDate", target = "publishedAt")
    @Mapping(source = "category.name", target = "category")
    @Mapping(source = "publication.author.email", target = "authorEmail")
    @Mapping(source = "publication", target = "trainings", qualifiedByName = "flattenAndMapTrainings")
    public abstract TrainingPlanPublicationDto toDto(TrainingPlanPublication publication);

    @Named("flattenAndMapTrainings")
    public List<TrainingPublicationDto> flattenAndMapTrainings(TrainingPlanPublication publication) {
        if (publication == null || publication.getBlocks() == null) {
            return List.of();
        }
        return publication.getBlocks().stream()
                .flatMap(block -> block.getTrainings().stream())
                .map(trainingPublicationMapper::toDto)
                .sorted(Comparator.comparing(TrainingPublicationDto::getDate))
                .collect(Collectors.toList());
    }

}
