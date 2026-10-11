
package com.sentinelai.sentinelai.controller;

import com.sentinelai.sentinelai.dto.EventIngestRequest;
import com.sentinelai.sentinelai.dto.EventIngestResponse;
import com.sentinelai.sentinelai.service.EventIngestionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventIngestionService eventIngestionService;

    public EventController(EventIngestionService eventIngestionService) {
        this.eventIngestionService = eventIngestionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public EventIngestResponse ingestEvent(
            @Valid @RequestBody EventIngestRequest request) {

        return eventIngestionService.ingest(request);
    }
}
