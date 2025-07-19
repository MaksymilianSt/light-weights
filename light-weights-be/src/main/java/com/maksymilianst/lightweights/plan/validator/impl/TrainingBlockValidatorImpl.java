package com.maksymilianst.lightweights.plan.validator.impl;

import com.maksymilianst.lightweights.plan.dto.TrainingBlockDto;
import com.maksymilianst.lightweights.plan.validator.TrainingBlockValidator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TrainingBlockValidatorImpl implements TrainingBlockValidator {

    @Override
    public void validate(TrainingBlockDto block) {
        validateDates(block.getStart(), block.getEnd());
    }

    private void validateDates(LocalDate start, LocalDate end) {
        if (start == null)
            throw new IllegalArgumentException("Start date cannot be null");

        if (end == null)
            throw new IllegalArgumentException("End date cannot be null");

        if (start.isAfter(end))
            throw new IllegalArgumentException("Start date cannot be after end date");
    }
}
