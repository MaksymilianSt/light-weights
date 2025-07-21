package com.maksymilianst.lightweights.plan.controller;

import com.maksymilianst.lightweights.plan.dto.TrainingDto;
import com.maksymilianst.lightweights.plan.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping(TrainingController.URL)
@RequiredArgsConstructor
public class TrainingController {
    public final static String URL = TrainingBlockController.URL + "/{blockId}/trainings";

    private final TrainingService trainingService;

    @GetMapping("/{trainingId}")
    @PreAuthorize("@planAccessControlService.hasAccessToTraining(#trainingId, principal.id)")
    public ResponseEntity<TrainingDto> getTrainingById(@PathVariable("trainingId") Integer trainingId) {
        return ResponseEntity.ok(
                trainingService.getById(trainingId)
        );
    }

    @PostMapping
    @PreAuthorize("@planAccessControlService.hasAccessToBlock(#blockId, principal.id)")
    public ResponseEntity<TrainingDto> create(
            @PathVariable("planId") Integer planId,
            @PathVariable("blockId") Integer blockId,
            @RequestBody TrainingDto trainingDto,
            UriComponentsBuilder uriBuilder
    ) {
        TrainingDto createdTraining = trainingService.create(blockId, trainingDto);

        URI uri = uriBuilder
                .path(URL + "/{trainingId}")
                .buildAndExpand(planId, blockId, createdTraining.getId())
                .toUri();

        return ResponseEntity.created(uri)
                .body(createdTraining);
    }

    @PutMapping("/{trainingId}")
    @PreAuthorize("@planAccessControlService.hasAccessToTraining(#trainingId, principal.id)")
    public ResponseEntity<TrainingDto> update(@PathVariable("trainingId") Integer trainingId, @RequestBody TrainingDto trainingDto) {
        return ResponseEntity.ok(
                trainingService.update(trainingId, trainingDto)
        );
    }

    @DeleteMapping("/{trainingId}")
    @PreAuthorize("@planAccessControlService.hasAccessToTraining(#trainingId, principal.id)")
    public ResponseEntity<?> delete(@PathVariable("trainingId") Integer trainingId) {
        trainingService.delete(trainingId);
        return ResponseEntity.noContent().build();
    }
}
