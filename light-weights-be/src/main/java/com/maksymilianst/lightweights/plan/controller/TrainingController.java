package com.maksymilianst.lightweights.plan.controller;

import com.maksymilianst.lightweights.plan.dto.TrainingDto;
import com.maksymilianst.lightweights.plan.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/plans/{planId}/blocks/{blockId}/trainings")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingService trainingService;

    @GetMapping("/{trainingId}")
    @PreAuthorize("@planAccessControlService.hasAccessToTraining(#trainingId, principal.id)")
    public ResponseEntity<TrainingDto> getTrainingById(@PathVariable("trainingId") Integer trainingId) {
        return ResponseEntity.ok(
                trainingService.getById(trainingId)
        );
    }
}
