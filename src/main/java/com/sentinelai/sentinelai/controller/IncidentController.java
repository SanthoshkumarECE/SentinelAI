
package com.sentinelai.sentinelai.controller;

import com.sentinelai.sentinelai.dto.IncidentDetailResponse;
import com.sentinelai.sentinelai.dto.IncidentSummaryResponse;
import com.sentinelai.sentinelai.service.IncidentQueryService;
import com.sentinelai.sentinelai.exception.IncidentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/incidents")
public class IncidentController {

    private final IncidentQueryService incidentQueryService;

    public IncidentController(IncidentQueryService incidentQueryService) {
        this.incidentQueryService = incidentQueryService;
    }

    @GetMapping
    public List<IncidentSummaryResponse> getAllIncidents() {
        return incidentQueryService.getAllIncidents();
    }


    @GetMapping("/{incidentId}")
    public IncidentDetailResponse getIncidentById(
            @PathVariable UUID incidentId) {

        return incidentQueryService.getIncidentById(incidentId);
    }

}
