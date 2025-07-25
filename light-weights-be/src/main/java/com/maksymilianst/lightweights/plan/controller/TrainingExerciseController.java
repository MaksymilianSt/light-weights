package com.maksymilianst.lightweights.plan.controller;

import com.maksymilianst.lightweights.plan.dto.TrainingExerciseDto;
import com.maksymilianst.lightweights.plan.service.TrainingExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping(TrainingExerciseController.URL)
@RequiredArgsConstructor
public class TrainingExerciseController {
    public final static String URL = TrainingController.URL + "/{trainingId}/exercises";

    private final TrainingExerciseService trainingExerciseService;

    @PostMapping
    @PreAuthorize("@planAccessControlService.hasAccessToTraining(#trainingId, principal.id)")
    public ResponseEntity<TrainingExerciseDto> create(
            @PathVariable("planId") Integer planId,
            @PathVariable("blockId") Integer blockId,
            @PathVariable("trainingId") Integer trainingId,
            @Validated @RequestBody TrainingExerciseDto exerciseDto,
            UriComponentsBuilder uriBuilder
    ) {
        TrainingExerciseDto createdExercise = trainingExerciseService.create(trainingId, exerciseDto);

        URI uri = uriBuilder
                .path(URL + "/{exerciseId}")
                .buildAndExpand(planId, blockId, trainingId, createdExercise.getId())
                .toUri();

        return ResponseEntity.created(uri)
                .body(createdExercise);
    }

    @PutMapping("/{exerciseId}")
    @PreAuthorize("@planAccessControlService.hasAccessToTraining(#trainingId, principal.id)")
    public ResponseEntity<TrainingExerciseDto> update(
            @PathVariable("trainingId") Integer trainingId,
            @PathVariable("exerciseId") Integer exerciseId,
            @Validated @RequestBody TrainingExerciseDto trainingExerciseDto
    ) {
        return ResponseEntity.ok(
                trainingExerciseService.update(exerciseId, trainingExerciseDto)
        );
    }

    @DeleteMapping("/{exerciseId}")
    @PreAuthorize("@planAccessControlService.hasAccessToTraining(#trainingId, principal.id)")
    public ResponseEntity<?> delete(@PathVariable("trainingId") Integer trainingId, @PathVariable("exerciseId") Integer exerciseId) {
        trainingExerciseService.delete(exerciseId);
        return ResponseEntity.noContent().build();
    }

}
