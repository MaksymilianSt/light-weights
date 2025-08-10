package com.maksymilianst.lightweights.publication.controller;

import com.maksymilianst.lightweights.plan.controller.TrainingPlanController;
import com.maksymilianst.lightweights.plan.dto.TrainingPlanDto;
import com.maksymilianst.lightweights.publication.dto.PlanScale;
import com.maksymilianst.lightweights.publication.dto.TrainingPlanPublicationDto;
import com.maksymilianst.lightweights.publication.service.PlanPublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(TrainingPlanPublicationController.URL)
@RequiredArgsConstructor
public class TrainingPlanPublicationController {
    public final static String URL = "/api/plan-publication";
    private final PlanPublicationService planPublicationService;

    //    @GetMapping todo preview
//    public ResponseEntity<List<TrainingPlanPublicationDto>> getAllPublications() {
//        return ResponseEntity.ok(planPublicationService.getAll());
//    }

    @GetMapping("/{publicationId}")
    public ResponseEntity<TrainingPlanPublicationDto> getById(@PathVariable Integer publicationId) {
        return ResponseEntity.ok(planPublicationService.getById(publicationId));
    }

    @PostMapping("/publish")
    @PreAuthorize("@planAccessControlService.hasAccessToPlan(#planId, principal.id)")
    public ResponseEntity<TrainingPlanPublicationDto> publish(@RequestParam("planId") Integer planId) {
        TrainingPlanPublicationDto publishedPlan = planPublicationService.publishPlan(planId);

        return ResponseEntity.created(URI.create(URL + "/" + publishedPlan.getId()))
                .body(publishedPlan);
    }

    @PostMapping("/download")
    public ResponseEntity<TrainingPlanDto> download(@RequestParam("publicationId") Integer publicationId, @RequestBody PlanScale scale) {
        TrainingPlanDto trainingPlanDto = planPublicationService.downloadPlan(publicationId, scale);

        return ResponseEntity.created(
                        URI.create(TrainingPlanController.URL + "/" + trainingPlanDto.getId())
                )
                .body(trainingPlanDto);
    }

    @DeleteMapping("/{publicationId}")
    @PreAuthorize("@planPublicationAccessControlService.hasRemoveAccessToPlanPublication(#publicationId, principal.id)")
    ResponseEntity<Void> delete(@PathVariable("publicationId") Integer publicationId) {
        planPublicationService.deletePublishedPlan(publicationId);
        return ResponseEntity.noContent().build();
    }
}
