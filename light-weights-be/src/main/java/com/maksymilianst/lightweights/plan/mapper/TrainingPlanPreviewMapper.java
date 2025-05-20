package com.maksymilianst.lightweights.plan.mapper;

import com.maksymilianst.lightweights.plan.dto.TrainingPlanPreviewDto;
import com.maksymilianst.lightweights.plan.model.TrainingPlan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrainingPlanPreviewMapper {

    @Mapping(source = "auditInfo.createdAt", target = "createdAt")
    @Mapping(source = "auditInfo.updatedAt", target = "updatedAt")
    @Mapping(source = "category.name", target = "category")
    TrainingPlanPreviewDto toDto(TrainingPlan trainingPlan);
}
