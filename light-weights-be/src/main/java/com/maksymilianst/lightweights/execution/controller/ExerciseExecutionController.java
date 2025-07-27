package com.maksymilianst.lightweights.execution.controller;

import com.maksymilianst.lightweights.execution.dto.TrainingExerciseExecutionDto;
import com.maksymilianst.lightweights.execution.service.TrainingExerciseExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(ExerciseExecutionController.URL)
@RequiredArgsConstructor
public class ExerciseExecutionController {
    public static final String URL =  TrainingExecutionController.URL + "/{executionId}/exercises";

    private final TrainingExerciseExecutionService trainingExerciseExecutionService;


    @PutMapping("/{exerciseId}")
    @PreAuthorize("@executionAccessControlService.hasAccessToExecution(#executionId, principal.id)")
    ResponseEntity<TrainingExerciseExecutionDto> update(
            @PathVariable("executionId") Integer executionId,
            @PathVariable("exerciseId") Integer exerciseId,
            @Validated @RequestBody TrainingExerciseExecutionDto trainingExerciseExecutionDto
    ){
        TrainingExerciseExecutionDto updated = trainingExerciseExecutionService.update(exerciseId, trainingExerciseExecutionDto);

        return ResponseEntity.ok(updated);
    }

}
