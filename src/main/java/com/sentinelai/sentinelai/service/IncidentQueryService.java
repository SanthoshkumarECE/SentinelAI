
package com.sentinelai.sentinelai.service;

import com.sentinelai.sentinelai.dto.EventResponse;
import com.sentinelai.sentinelai.dto.IncidentDetailResponse;
import com.sentinelai.sentinelai.dto.IncidentSummaryResponse;
import com.sentinelai.sentinelai.entity.Event;
import com.sentinelai.sentinelai.entity.Incident;
import com.sentinelai.sentinelai.exception.IncidentNotFoundException;
import com.sentinelai.sentinelai.repository.IncidentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class IncidentQueryService {

    private final IncidentRepository incidentRepository;

    public IncidentQueryService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    @Transactional(readOnly = true)
    public List<IncidentSummaryResponse> getAllIncidents() {
        return incidentRepository.findAll()
                .stream()
                .map(incident -> new IncidentSummaryResponse(
                        incident.getIncidentId(),
                        incident.getTitle(),
                        incident.getSeverity(),
                        incident.getStatus(),
                        incident.getOccurrenceCount(),
                        incident.getFirstSeen(),
                        incident.getLastSeen()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public IncidentDetailResponse getIncidentById(UUID incidentId) {
        Incident incident = incidentRepository.findById(incidentId)
                .orElseThrow(() ->
                        new IncidentNotFoundException(incidentId));

        List<EventResponse> eventResponses = incident.getEvents()
                .stream()
                .map(this::toEventResponse)
                .toList();

        return new IncidentDetailResponse(
                incident.getIncidentId(),
                incident.getTitle(),
                incident.getSeverity(),
                incident.getStatus(),
                incident.getFingerprint(),
                incident.getOccurrenceCount(),
                incident.getFirstSeen(),
                incident.getLastSeen(),
                eventResponses
        );
    }

    private EventResponse toEventResponse(Event event) {
        return new EventResponse(
                event.getEventId(),
                event.getServiceName(),
                event.getLevel(),
                event.getMessage(),
                event.getTraceId(),
                event.getReceivedAt()
        );
    }
}
