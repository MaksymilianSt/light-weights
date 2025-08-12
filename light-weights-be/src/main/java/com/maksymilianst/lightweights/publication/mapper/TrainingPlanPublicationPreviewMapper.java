package com.maksymilianst.lightweights.publication.mapper;

import com.maksymilianst.lightweights.plan.model.TrainingBlock;
import com.maksymilianst.lightweights.publication.dto.TrainingPlanPublicationPreviewDto;
import com.maksymilianst.lightweights.publication.model.TrainingPlanPublication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.util.Comparator;


@Mapper(componentModel = "spring", uses = {TrainingPublicationMapper.class, TrainingPlanPublicationOpinionMapper.class})
public interface TrainingPlanPublicationPreviewMapper {

    @Mapping(source = "publicationDate", target = "publishedAt")
    @Mapping(source = "category.name", target = "category")
    @Mapping(source = "publication.author.email", target = "authorEmail")
    @Mapping(target = "downloads", expression = "java(publication.getUserUniqueDownloads().size())")
    @Mapping(source = "publication", target = "start", qualifiedByName = "calculateStart")
    @Mapping(source = "publication", target = "end", qualifiedByName = "calculateEnd")
    TrainingPlanPublicationPreviewDto toDto(TrainingPlanPublication publication);

    @Named("calculateStart")
    default LocalDate calculateStart(TrainingPlanPublication publication) {
        return publication.getBlocks().stream()
                .map(TrainingBlock::getStart)
                .min(Comparator.naturalOrder())
                .orElse(null);
    }

    @Named("calculateEnd")
    default LocalDate calculateEnd(TrainingPlanPublication publication) {
        return publication.getBlocks().stream()
                .map(TrainingBlock::getEnd)
                .max(Comparator.naturalOrder())
                .orElse(null);
    }

}
