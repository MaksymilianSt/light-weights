package com.maksymilianst.lightweights.plan.controller;

import com.maksymilianst.lightweights.plan.dto.TrainingPlanPreviewDto;
import com.maksymilianst.lightweights.plan.service.TrainingPlanService;
import com.maksymilianst.lightweights.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class TrainingPlanController {
    private final TrainingPlanService trainingPlanService;

    @GetMapping
    public ResponseEntity<List<TrainingPlanPreviewDto>> getPlans(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
                trainingPlanService.getAllForUser(user.getId())
        );
    }
}
