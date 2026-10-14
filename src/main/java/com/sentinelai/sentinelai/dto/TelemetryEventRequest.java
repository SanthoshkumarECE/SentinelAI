package com.sentinelai.sentinelai.dto;

public record TelemetryEventRequest(
        String serviceName,
        String level,
        String message,
        String traceId
) {
}