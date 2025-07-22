package com.maksymilianst.lightweights.plan.controller;

import com.maksymilianst.lightweights.plan.dto.TrainingBlockDto;
import com.maksymilianst.lightweights.plan.service.TrainingBlockService;
import com.maksymilianst.lightweights.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping(TrainingBlockController.URL)
@RequiredArgsConstructor
public class TrainingBlockController {
    public final static String URL = TrainingPlanController.URL + "/{planId}/blocks";

    private final TrainingBlockService trainingBlockService;

    @GetMapping("/{blockId}")
    @PreAuthorize("@planAccessControlService.hasAccessToBlock(#blockId, principal.id)")
    public ResponseEntity<TrainingBlockDto> getTrainingById(@PathVariable("blockId") Integer blockId) {
        return ResponseEntity.ok(
                trainingBlockService.getById(blockId)
        );
    }

    @PostMapping
    @PreAuthorize("@planAccessControlService.hasAccessToPlan(#planId, principal.id)")
    public ResponseEntity<TrainingBlockDto> create(
            @PathVariable("planId") Integer planId,
            @Validated @RequestBody TrainingBlockDto trainingBlockDto,
            @AuthenticationPrincipal User user,
            UriComponentsBuilder uriBuilder
    ) {
        TrainingBlockDto createdBlock = trainingBlockService.create(planId, trainingBlockDto, user);

        URI uri = uriBuilder
                .path(URL + "/{blockId}")
                .buildAndExpand(planId, createdBlock.getId())
                .toUri();

        return ResponseEntity.created(uri)
                .body(createdBlock);
    }

    @PutMapping("/{blockId}")
    @PreAuthorize("@planAccessControlService.hasAccessToBlock(#blockId, principal.id)")
    public ResponseEntity<TrainingBlockDto> update(@PathVariable("blockId") Integer blockId, @Validated @RequestBody TrainingBlockDto trainingBlockDto) {
        return ResponseEntity.ok(
                trainingBlockService.update(blockId, trainingBlockDto)
        );
    }

    @DeleteMapping("/{blockId}")
    @PreAuthorize("@planAccessControlService.hasAccessToBlock(#blockId, principal.id)")
    public ResponseEntity<?> delete(@PathVariable("blockId") Integer blockId) {
        trainingBlockService.delete(blockId);
        return ResponseEntity.noContent().build();
    }

}
