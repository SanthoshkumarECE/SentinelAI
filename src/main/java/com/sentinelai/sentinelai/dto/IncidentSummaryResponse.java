
package com.sentinelai.sentinelai.dto;

import java.time.Instant;
import java.util.UUID;

public record IncidentSummaryResponse(
        UUID incidentId,
        String title,
        String severity,
        String status,
        long occurrenceCount,
        Instant firstSeen,
        Instant lastSeen
) {
}

