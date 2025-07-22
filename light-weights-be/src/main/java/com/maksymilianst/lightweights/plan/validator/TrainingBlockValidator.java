package com.maksymilianst.lightweights.plan.validator;

import com.maksymilianst.lightweights.plan.dto.TrainingBlockDto;
import com.maksymilianst.lightweights.plan.model.Training;

import java.util.Collection;

public interface TrainingBlockValidator {

    void validate(TrainingBlockDto block);

    void validate(TrainingBlockDto block, Collection<Training> blockTrainings);
}
