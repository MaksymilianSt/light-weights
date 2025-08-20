package com.maksymilianst.lightweights.plan.validator.impl;

import com.maksymilianst.lightweights.plan.dto.TrainingDto;
import com.maksymilianst.lightweights.plan.model.TrainingBlock;
import com.maksymilianst.lightweights.plan.validator.TrainingValidator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
class TrainingValidatorImpl implements TrainingValidator {

    @Override
    public void validate(TrainingDto training, TrainingBlock block) {
       validateDate(training.getDate(), block);
    }

    private void validateDate(LocalDate trainingDate, TrainingBlock block) {
        if (block.getStart().isAfter(trainingDate) || block.getEnd().isBefore(trainingDate)) {
            throw new IllegalArgumentException("Training date must be in Block start and end range");
        }
    }
}
