package com.maksymilianst.lightweights.plan.service.impl;

import com.maksymilianst.lightweights.plan.repository.TrainingBlockRepository;
import com.maksymilianst.lightweights.plan.repository.TrainingPlanRepository;
import com.maksymilianst.lightweights.plan.repository.TrainingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlanAccessControlServiceImplTest {

    private PlanAccessControlServiceImpl accessControlService;

    @Mock
    private TrainingPlanRepository planRepository;
    @Mock
    private TrainingBlockRepository blockRepository;
    @Mock
    private TrainingRepository trainingRepository;

    private final Integer planId = 1;
    private final Integer userId = 100;

    @BeforeEach
    void setUp() {
        accessControlService = new PlanAccessControlServiceImpl(planRepository, blockRepository, trainingRepository);
    }

    @Test
    void hasAccessToPlan_shouldReturnTrue_whenAccessExists() {
        when(planRepository.existsByIdAndUserId(planId, userId)).thenReturn(true);

        boolean result = accessControlService.hasAccessToPlan(planId, userId);

        assertTrue(result);
        verify(planRepository).existsByIdAndUserId(planId, userId);
    }

    @Test
    void hasAccessToPlan_shouldReturnFalse_whenAccessDoesNotExist() {
        when(planRepository.existsByIdAndUserId(planId, userId)).thenReturn(false);

        boolean result = accessControlService.hasAccessToPlan(planId, userId);

        assertFalse(result);
        verify(planRepository).existsByIdAndUserId(planId, userId);
    }

}
