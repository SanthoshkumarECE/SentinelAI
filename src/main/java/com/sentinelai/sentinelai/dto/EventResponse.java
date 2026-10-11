
package com.sentinelai.sentinelai.dto;

import java.time.Instant;
import java.util.UUID;

public record EventResponse(
        UUID eventId,
        String serviceName,
        String level,
        String message,
        String traceId,
        Instant receivedAt
) {
}
