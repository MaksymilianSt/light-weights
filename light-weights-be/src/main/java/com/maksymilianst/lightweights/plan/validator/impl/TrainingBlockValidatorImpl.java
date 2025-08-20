package com.maksymilianst.lightweights.plan.validator.impl;

import com.maksymilianst.lightweights.plan.dto.TrainingBlockDto;
import com.maksymilianst.lightweights.plan.model.Training;
import com.maksymilianst.lightweights.plan.validator.TrainingBlockValidator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;

@Component
class TrainingBlockValidatorImpl implements TrainingBlockValidator {

    @Override
    public void validate(TrainingBlockDto block) {
        validateDates(block.getStart(), block.getEnd());
    }

    @Override
    public void validate(TrainingBlockDto block, Collection<Training> blockTrainings) {
        validateDates(block.getStart(), block.getEnd());
        validateTrainingDatesInBlockRange(block.getStart(), block.getEnd(), blockTrainings);
    }

    private void validateDates(LocalDate start, LocalDate end) {
        if (start == null)
            throw new IllegalArgumentException("Start date cannot be null");

        if (end == null)
            throw new IllegalArgumentException("End date cannot be null");

        if (start.isAfter(end))
            throw new IllegalArgumentException("Start date cannot be after end date");
    }

    private void validateTrainingDatesInBlockRange(LocalDate start, LocalDate end, Collection<Training> blockTrainings) {
        blockTrainings.stream()
                .map(Training::getDate)
                .filter(date -> date.isBefore(start) || date.isAfter(end))
                .findAny()
                .ifPresent(date -> { throw new IllegalArgumentException("Training date out of block range"); });
    }

}
