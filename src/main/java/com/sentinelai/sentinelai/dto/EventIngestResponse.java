package com.sentinelai.sentinelai.dto;

import java.time.Instant;
import java.util.UUID;

public record EventIngestResponse(
        UUID eventId,
        String status,
        Instant receivedAt
) {
}
