package com.maksymilianst.lightweights.plan.validator;


import com.maksymilianst.lightweights.plan.dto.TrainingDto;
import com.maksymilianst.lightweights.plan.model.TrainingBlock;


public interface TrainingValidator {

    void validate(TrainingDto trainingDto, TrainingBlock block);
}
