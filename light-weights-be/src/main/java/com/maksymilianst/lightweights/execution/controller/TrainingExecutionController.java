package com.maksymilianst.lightweights.execution.controller;

import com.maksymilianst.lightweights.execution.dto.TrainingExecutionDto;
import com.maksymilianst.lightweights.execution.service.TrainingExecutionService;
import com.maksymilianst.lightweights.user.User;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(TrainingExecutionController.URL)
@RequiredArgsConstructor
public class TrainingExecutionController {
    public static final String URL = "/api/executions";

    private final TrainingExecutionService trainingExecutionService;

    @GetMapping
    ResponseEntity<List<TrainingExecutionDto>> getAll(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
                trainingExecutionService.getAllForUser(user)
        );
    }

    @GetMapping("/{executionId}")
    @PreAuthorize("@executionAccessControlService.hasAccessToExecution(#executionId, principal.id)")
    ResponseEntity<TrainingExecutionDto> getById(@PathVariable Integer executionId) {
        return ResponseEntity.ok(
                trainingExecutionService.getById(executionId)
        );
    }

    @PostMapping
    @PreAuthorize("@planAccessControlService.hasAccessToTraining(#trainingId, principal.id)")
    ResponseEntity<TrainingExecutionDto> create(@NotNull @Param("trainingId") Integer trainingId, @AuthenticationPrincipal User user) {
        TrainingExecutionDto created = trainingExecutionService.create(trainingId, user);
        return ResponseEntity
                .created(URI.create(TrainingExecutionController.URL + "/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{executionId}")
    @PreAuthorize("@executionAccessControlService.hasAccessToExecution(#executionId, principal.id)")
    ResponseEntity<TrainingExecutionDto> update(@PathVariable("executionId") Integer executionId, @RequestBody TrainingExecutionDto trainingExecutionDto) {
        TrainingExecutionDto updated = trainingExecutionService.update(executionId, trainingExecutionDto);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{executionId}")
    @PreAuthorize("@executionAccessControlService.hasAccessToExecution(#executionId, principal.id)")
    ResponseEntity<TrainingExecutionDto> finish(@PathVariable("executionId") Integer executionId) {
        TrainingExecutionDto updated = trainingExecutionService.finish(executionId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{executionId}")
    @PreAuthorize("@executionAccessControlService.hasAccessToExecution(#executionId, principal.id)")
    ResponseEntity<?> deleteById(@PathVariable Integer executionId) {
        trainingExecutionService.deleteById(executionId);
        return ResponseEntity.noContent().build();
    }

}
