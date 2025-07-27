package com.maksymilianst.lightweights.execution.service;

public interface ExecutionAccessControlService {
    boolean hasAccessToExecution(Integer trainingId, Integer userId);
}
