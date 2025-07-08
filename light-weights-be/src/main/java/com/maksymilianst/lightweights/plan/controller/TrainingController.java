package com.maksymilianst.lightweights.plan.controller;

import com.maksymilianst.lightweights.plan.dto.TrainingDto;
import com.maksymilianst.lightweights.plan.service.TrainingService;
import com.maksymilianst.lightweights.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<TrainingDto> getPlanById(@PathVariable("trainingId") Integer id, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
                trainingService.getByIdForUser(id)
        );
    }
}
