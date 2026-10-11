
package com.sentinelai.sentinelai.service;

import com.sentinelai.sentinelai.detection.IncidentFingerprint;
import com.sentinelai.sentinelai.dto.EventIngestRequest;
import com.sentinelai.sentinelai.dto.EventIngestResponse;
import com.sentinelai.sentinelai.entity.Event;
import com.sentinelai.sentinelai.entity.Incident;
import com.sentinelai.sentinelai.repository.EventRepository;
import com.sentinelai.sentinelai.repository.IncidentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class EventIngestionService {

    private final EventRepository eventRepository;
    private final IncidentRepository incidentRepository;

    public EventIngestionService(
            EventRepository eventRepository,
            IncidentRepository incidentRepository) {
        this.eventRepository = eventRepository;
        this.incidentRepository = incidentRepository;
    }

    @Transactional
    public EventIngestResponse ingest(EventIngestRequest request) {
        Instant now = Instant.now();

        String fingerprint = IncidentFingerprint.generate(request);

        Incident incident = incidentRepository
                .findFirstByFingerprintAndStatus(fingerprint, "OPEN")
                .orElseGet(() -> incidentRepository.save(
                        new Incident(
                                request.message(),
                                fingerprint,
                                determineSeverity(request.level()),
                                now
                        )
                ));

        incident.recordOccurrence(now);

        Event event = new Event(
                request.serviceName(),
                request.level(),
                request.message(),
                request.traceId()
        );

        event.assignIncident(incident);

        Event saved = eventRepository.save(event);

        return new EventIngestResponse(
                saved.getEventId(),
                "ACCEPTED",
                saved.getReceivedAt()
        );
    }

    private String determineSeverity(String level) {
        return switch (level.toUpperCase()) {
            case "ERROR" -> "HIGH";
            case "WARN" -> "MEDIUM";
            default -> "LOW";
        };
    }
}
