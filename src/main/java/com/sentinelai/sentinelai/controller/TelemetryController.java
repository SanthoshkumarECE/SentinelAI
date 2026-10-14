package com.sentinelai.sentinelai.controller;

import com.sentinelai.sentinelai.dto.EventIngestRequest;
import com.sentinelai.sentinelai.dto.EventIngestResponse;
import com.sentinelai.sentinelai.dto.TelemetryEventRequest;
import com.sentinelai.sentinelai.service.EventIngestionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/telemetry")
public class TelemetryController {

    private final EventIngestionService eventIngestionService;

    public TelemetryController(EventIngestionService eventIngestionService) {
        this.eventIngestionService = eventIngestionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public EventIngestResponse ingestTelemetry(
            @Valid @RequestBody TelemetryEventRequest telemetry) {

        EventIngestRequest event = new EventIngestRequest(
                telemetry.serviceName(),
                telemetry.level(),
                telemetry.message(),
                telemetry.traceId()
        );

        return eventIngestionService.ingest(event);
    }
}