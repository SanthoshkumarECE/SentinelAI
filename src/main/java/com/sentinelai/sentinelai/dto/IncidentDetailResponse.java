
package com.sentinelai.sentinelai.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record IncidentDetailResponse(
        UUID incidentId,
        String title,
        String severity,
        String status,
        String fingerprint,
        long occurrenceCount,
        Instant firstSeen,
        Instant lastSeen,
        List<EventResponse> events
) {
}
