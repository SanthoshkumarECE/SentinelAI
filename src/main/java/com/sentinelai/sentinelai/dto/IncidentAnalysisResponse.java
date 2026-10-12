package com.sentinelai.sentinelai.dto;

import java.time.Instant;
import java.util.UUID;

public record IncidentAnalysisResponse(
        UUID incidentId,
        String status,
        String analysis,
        Instant analyzedAt
) {
}
