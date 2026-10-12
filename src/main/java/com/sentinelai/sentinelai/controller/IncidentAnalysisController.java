package com.sentinelai.sentinelai.controller;

import com.sentinelai.sentinelai.dto.IncidentAnalysisResponse;
import com.sentinelai.sentinelai.service.IncidentAnalysisService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/incidents")
public class IncidentAnalysisController {

    private final IncidentAnalysisService incidentAnalysisService;

    public IncidentAnalysisController(
            IncidentAnalysisService incidentAnalysisService) {
        this.incidentAnalysisService = incidentAnalysisService;
    }

    @GetMapping("/{incidentId}/analysis")
    public IncidentAnalysisResponse analyzeIncident(
            @PathVariable UUID incidentId) {
        return incidentAnalysisService.analyzeIncident(incidentId);
    }
}