package com.maksymilianst.lightweights.publication.service.impl;

import com.maksymilianst.lightweights.plan.model.*;
import com.maksymilianst.lightweights.plan.repository.ExerciseRepository;
import com.maksymilianst.lightweights.plan.repository.TrainingPlanRepository;
import com.maksymilianst.lightweights.publication.dto.PlanScale;
import com.maksymilianst.lightweights.publication.model.TrainingPlanPublication;
import com.maksymilianst.lightweights.publication.repository.TrainingPlanPublicationRepository;
import com.maksymilianst.lightweights.publication.service.CopyPlanService;
import com.maksymilianst.lightweights.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.stream.Collectors;

@Service()
@RequestScope
@RequiredArgsConstructor
public class CopyPlanServiceImpl implements CopyPlanService {
    private final ExerciseRepository exerciseRepository;
    private final TrainingPlanPublicationRepository publicationRepository;
    private final TrainingPlanRepository trainingPlanRepository;

    private PlanScale scale;

    @Override
    public TrainingPlanPublication fromPlan(TrainingPlan plan) {
        this.scale = PlanScale.builder()
                .weightScale(1.0)
                .daysOffSet(0)
                .build();

        TrainingPlanPublication publication = new TrainingPlanPublication();

        copyDetails(plan, publication);
        publication.setAuthor(plan.getUser());

        publication = publicationRepository.save(publication);
        copyBlocks(plan.getBlocks(), publication);

        return publication;
    }

    @Override
    public TrainingPlan fromPublication(TrainingPlanPublication publication, PlanScale scale) {
        LocalDate firstPlanDate = publication.getBlocks().stream()
                .map(TrainingBlock::getStart)
                .min(LocalDate::compareTo)
                .orElse(LocalDate.now());

        this.scale = PlanScale.builder()
                .weightScale(scale.getWeightScale())
                .daysOffSet(
                        (int) ChronoUnit.DAYS.between(firstPlanDate, scale.getPlanStartDate())
                )
                .build();

        TrainingPlan trainingPlan = new TrainingPlan();

        copyDetails(publication, trainingPlan);
        trainingPlan.setUser(
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        );

        trainingPlan = trainingPlanRepository.save(trainingPlan);
        copyBlocks(publication.getBlocks(), trainingPlan);

        return trainingPlan;
    }

    private void copyDetails(TrainingPlan src, TrainingPlan dest) {
        dest.setName(src.getName());
        dest.setDescription(src.getDescription());
        dest.setCategory(src.getCategory());
        dest.setDifficultyLvl(src.getDifficultyLvl());
        dest.setGoal(src.getGoal());
    }

    private void copyBlocks(Collection<TrainingBlock> src, TrainingPlan dest) {
        dest.getBlocks().addAll(
                src.stream()
                        .map(srcBlock -> copyBlock(srcBlock, dest))
                        .collect(Collectors.toSet())
        );
    }

    private TrainingBlock copyBlock(TrainingBlock src, TrainingPlan destParentPlan) {
        TrainingBlock newBlock = new TrainingBlock();

        newBlock.setName(src.getName());
        newBlock.setDescription(src.getDescription());
        newBlock.setStart(src.getStart().plusDays(scale.getDaysOffSet()));
        newBlock.setEnd(src.getEnd().plusDays(scale.getDaysOffSet()));
        newBlock.setPlan(destParentPlan);

        copyTrainings(src.getTrainings(), newBlock);

        return newBlock;
    }

    private void copyTrainings(Collection<Training> src, TrainingBlock dest) {
        dest.setTrainings(
                src.stream()
                        .map(srcTraining -> copyTraining(srcTraining, dest))
                        .collect(Collectors.toSet())
        );
    }

    private Training copyTraining(Training src, TrainingBlock destParentBlock) {
        Training newTraining = new Training();

        newTraining.setName(src.getName());
        newTraining.setDescription(src.getDescription());
        newTraining.setDate(src.getDate().plusDays(scale.getDaysOffSet()));

        newTraining.setBlock(destParentBlock);

        copyExercises(src.getExercises(), newTraining);

        return newTraining;
    }

    private void copyExercises(Collection<TrainingExercise> src, Training dest) {
        dest.setExercises(
                src.stream()
                        .map(trainingExercise -> copyExercise(trainingExercise, dest.getBlock().getPlan()))
                        .peek(trainingExercise -> trainingExercise.setTraining(dest))
                        .collect(Collectors.toSet())
        );
    }

    private TrainingExercise copyExercise(TrainingExercise src, TrainingPlan destParentPlan) {
        TrainingExercise newTrainingExercise = new TrainingExercise();

        newTrainingExercise.setDescription(src.getDescription());
        newTrainingExercise.setSequence(src.getSequence());
        newTrainingExercise.setExercise(
                findExerciseByNameAndPlanIdOrCreate(src.getExercise().getName(), destParentPlan)
        );

        copySets(src.getSets(), newTrainingExercise);

        return newTrainingExercise;

    }

    private void copySets(Collection<TrainingSet> src, TrainingExercise dest) {
        dest.setSets(
                src.stream()
                        .map(this::copySet)
                        .peek(set -> set.setExercise(dest))
                        .collect(Collectors.toSet())
        );
    }

    private TrainingSet copySet(TrainingSet src) {
        TrainingSet newSet = new TrainingSet();

        newSet.setRepetitions(src.getRepetitions());
        newSet.setWeight(
                roundUpToHalf(
                        src.getWeight().multiply(BigDecimal.valueOf(scale.getWeightScale()))
                )
        );
        newSet.setTempo(src.getTempo());
        newSet.setSequence(src.getSequence());

        return newSet;

    }

    public static BigDecimal roundUpToHalf(BigDecimal value) {
        return value
                .multiply(BigDecimal.valueOf(2))
                .setScale(0, RoundingMode.CEILING)
                .divide(BigDecimal.valueOf(2), 1, RoundingMode.UNNECESSARY);
    }


    private Exercise findExerciseByNameAndPlanIdOrCreate(String exerciseName, TrainingPlan plan) {
        return exerciseRepository
                .findByNameIgnoreCaseAndTrainingPlanId(exerciseName, plan.getId())
                .orElseGet(() -> {
                    Exercise newExercise = new Exercise();

                    newExercise.setName(exerciseName);
                    newExercise.setTrainingPlan(plan);

                    return exerciseRepository.save(newExercise);
                });
    }

}