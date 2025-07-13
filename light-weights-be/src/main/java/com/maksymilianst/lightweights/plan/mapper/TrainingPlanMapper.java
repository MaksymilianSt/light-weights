package com.maksymilianst.lightweights.plan.mapper;

import com.maksymilianst.lightweights.plan.dto.TrainingPlanDto;
import com.maksymilianst.lightweights.plan.model.PlanCategory;
import com.maksymilianst.lightweights.plan.model.TrainingPlan;
import com.maksymilianst.lightweights.plan.repository.PlanCategoryRepository;
import com.maksymilianst.lightweights.plan.model.DifficultyLevel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = "spring", uses = TrainingBlockMapper.class)
public abstract class TrainingPlanMapper {

    @Autowired
    protected PlanCategoryRepository categoryRepository;

    @Mapping(source = "auditInfo.createdAt", target = "createdAt")
    @Mapping(source = "auditInfo.updatedAt", target = "updatedAt")
    @Mapping(source = "category.name", target = "category")
    public abstract TrainingPlanDto toDto(TrainingPlan plan);

    @Mapping(target = "blocks", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", expression = "java(mapCategory(planDto.getCategory()))")
    @Mapping(target = "difficultyLvl", expression = "java(mapDifficultyLevel(planDto.getDifficultyLvl()))")
    public abstract TrainingPlan toEntity(TrainingPlanDto planDto);


    protected PlanCategory mapCategory(String categoryName) {
        return categoryRepository.findByNameIgnoreCase(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category name: " + categoryName));
    }

    protected DifficultyLevel mapDifficultyLevel(String difficultyLevel) {
        return DifficultyLevel.fromStringIgnoreCase(difficultyLevel);
    }

}
