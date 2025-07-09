package com.maksymilianst.lightweights.plan.service;

public interface PlanAccessControlService {

    boolean hasAccessToPlan(Integer planId, Integer userId);

    boolean hasAccessToBlock(Integer blockId, Integer userId);

    boolean hasAccessToTraining(Integer trainingId, Integer userId);
}
