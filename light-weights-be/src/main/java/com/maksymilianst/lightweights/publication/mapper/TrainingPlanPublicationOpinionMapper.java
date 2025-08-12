package com.maksymilianst.lightweights.publication.mapper;

import com.maksymilianst.lightweights.publication.dto.TrainingPlanPublicationOpinionDto;
import com.maksymilianst.lightweights.publication.model.TrainingPlanPublicationOpinion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainingPlanPublicationOpinionMapper {

    @Mapping(source = "createdAt", target = "creationTime")
    @Mapping(source = "user.email", target = "userEmail")
    TrainingPlanPublicationOpinionDto toDto(TrainingPlanPublicationOpinion opinion);

    default List<TrainingPlanPublicationOpinionDto> toDtos(Collection<TrainingPlanPublicationOpinion> opinions) {
        return opinions.stream()
                .map(this::toDto)
                .sorted(
                        Comparator.comparing(TrainingPlanPublicationOpinionDto::getCreationTime).reversed()
                )
                .toList();
    }
}
