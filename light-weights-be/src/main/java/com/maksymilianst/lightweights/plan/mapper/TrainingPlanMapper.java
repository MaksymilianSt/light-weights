package com.maksymilianst.lightweights.plan.mapper;

import com.maksymilianst.lightweights.plan.dto.TrainingPlanDto;
import com.maksymilianst.lightweights.plan.model.TrainingPlan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = TrainingBlockMapper.class)
public interface TrainingPlanMapper {

    @Mapping(source = "auditInfo.createdAt", target = "createdAt")
    @Mapping(source = "auditInfo.updatedAt", target = "updatedAt")
    @Mapping(source = "category.name", target = "category")
    TrainingPlanDto toDto(TrainingPlan plan);

}
