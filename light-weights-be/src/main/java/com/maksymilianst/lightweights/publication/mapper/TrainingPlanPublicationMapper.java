package com.maksymilianst.lightweights.publication.mapper;

import com.maksymilianst.lightweights.publication.dto.TrainingPlanPublicationDto;
import com.maksymilianst.lightweights.publication.model.TrainingPlanPublication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TrainingPublicationMapper.class)
public interface TrainingPlanPublicationMapper{

    @Mapping(source = "publicationDate", target = "publishedAt")
    @Mapping(source = "category.name", target = "category")
    TrainingPlanPublicationDto toDto(TrainingPlanPublication plan);
}
