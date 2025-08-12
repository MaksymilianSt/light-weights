package com.maksymilianst.lightweights.publication.controller;

import com.maksymilianst.lightweights.publication.dto.TrainingPlanPublicationOpinionDto;
import com.maksymilianst.lightweights.publication.service.PlanPublicationOpinionService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping(TrainingPlanPublicationOpinionController.URL)
@RequiredArgsConstructor
public class TrainingPlanPublicationOpinionController {
    public final static String URL = TrainingPlanPublicationController.URL + "/{planPublicationId}/opinions";

    private final PlanPublicationOpinionService planPublicationOpinionService;


    @PostMapping()
    public ResponseEntity<TrainingPlanPublicationOpinionDto> create
            (
                    @PathVariable("planPublicationId") Integer publicationId,
                    @RequestBody String content,
                    UriComponentsBuilder uriBuilder
            ) {
        TrainingPlanPublicationOpinionDto opinionDto = planPublicationOpinionService.create(publicationId, content);

        URI uri = uriBuilder
                .path(URL + "/{opinionId}")
                .buildAndExpand(publicationId, opinionDto.getId())
                .toUri();

        return ResponseEntity.created(uri)
                .body(opinionDto);
    }

    @DeleteMapping("/{opinionId}")
    @RolesAllowed({"ADMIN"})
    ResponseEntity<Void> delete(@PathVariable("opinionId") Integer opinionId) {
        planPublicationOpinionService.delete(opinionId);
        return ResponseEntity.noContent().build();
    }
}
