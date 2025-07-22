package com.maksymilianst.lightweights.plan.controller;

import com.maksymilianst.lightweights.plan.dto.TrainingPlanDto;
import com.maksymilianst.lightweights.plan.dto.TrainingPlanPreviewDto;
import com.maksymilianst.lightweights.plan.service.TrainingPlanService;
import com.maksymilianst.lightweights.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(TrainingPlanController.URL)
@RequiredArgsConstructor
public class TrainingPlanController {
    public final static String URL = "/api/plans";

    private final TrainingPlanService trainingPlanService;

    @GetMapping
    public ResponseEntity<List<TrainingPlanPreviewDto>> getPlans(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
                trainingPlanService.getAllForUser(user.getId())
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("@planAccessControlService.hasAccessToPlan(#id, principal.id)")
    public ResponseEntity<TrainingPlanDto> getPlanById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(
                trainingPlanService.getById(id)
        );
    }

    @PostMapping
    public ResponseEntity<TrainingPlanDto> create(@Validated @RequestBody TrainingPlanDto trainingPlanDto, @AuthenticationPrincipal User user) {
        TrainingPlanDto createdPlan = trainingPlanService.create(trainingPlanDto, user);

        return ResponseEntity.created(URI.create(URL + "/" + createdPlan.getId()))
                .body(createdPlan);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@planAccessControlService.hasAccessToPlan(#id, principal.id)")
    public ResponseEntity<TrainingPlanDto> update(@PathVariable("id") Integer id, @Validated @RequestBody TrainingPlanDto trainingPlanDto) {
        return ResponseEntity.ok(
                trainingPlanService.update(id, trainingPlanDto)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@planAccessControlService.hasAccessToPlan(#id, principal.id)")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        trainingPlanService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        return ResponseEntity.ok(
                trainingPlanService.getCategories()
        );
    }

    @GetMapping("/difficulty-lvls")
    public ResponseEntity<List<String>> getDifficultyLvls() {
        return ResponseEntity.ok(
                trainingPlanService.getDifficultyLvls()
        );
    }

}
