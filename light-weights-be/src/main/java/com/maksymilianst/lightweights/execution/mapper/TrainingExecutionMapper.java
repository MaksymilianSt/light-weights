package com.maksymilianst.lightweights.execution.mapper;

import com.maksymilianst.lightweights.execution.dto.TrainingExecutionDto;
import com.maksymilianst.lightweights.execution.dto.TrainingExecutionPreviewDto;
import com.maksymilianst.lightweights.execution.model.TrainingExecution;
import com.maksymilianst.lightweights.execution.model.TrainingExerciseExecution;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Mapper(componentModel = "spring", uses = ExerciseExecutionMapper.class)
public interface TrainingExecutionMapper {

    @Mapping(source = "trainingExecution.referencedTraining.name", target = "referencedTrainingName")
    TrainingExecutionDto toDto(TrainingExecution trainingExecution);

    @Mapping(source = "trainingExecution.referencedTraining.name", target = "trainingName")
    @Mapping(source = "trainingExecution.referencedTraining.block.plan.name", target = "planName")
    @Mapping(source = "trainingExecution", target = "completionPercentage", qualifiedByName = "completion")
    @Mapping(target = "finished", expression = "java(trainingExecution.getFinishDate() != null)")
    TrainingExecutionPreviewDto toPreviewDto(TrainingExecution trainingExecution);


    @Named("completion")
    default double calculateCompletion(TrainingExecution execution) {
        double completed = (double) execution.getTrainingExerciseExecutions().stream()
                .filter(TrainingExerciseExecution::isDone)
                .count();

        return (completed / execution.getTrainingExerciseExecutions().size()) * 100;
    }

    default List<TrainingExecutionPreviewDto> toPreviewDtos(Collection<TrainingExecution> trainingExecutions) {
        return trainingExecutions.stream()
                .map(this::toPreviewDto)
                .sorted(byUnfinishedFirstAndStartDateDesc())
                .toList();
    }

    private static Comparator<TrainingExecutionPreviewDto> byUnfinishedFirstAndStartDateDesc() {
        return Comparator.comparing(TrainingExecutionPreviewDto::getFinished)
                .thenComparing(Comparator.comparing(TrainingExecutionPreviewDto::getStartDate).reversed());
    }

}
