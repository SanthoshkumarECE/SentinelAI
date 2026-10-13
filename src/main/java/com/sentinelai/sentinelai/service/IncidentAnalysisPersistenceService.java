
package com.sentinelai.sentinelai.service;

import com.sentinelai.sentinelai.entity.Incident;
import com.sentinelai.sentinelai.entity.IncidentAnalysis;
import com.sentinelai.sentinelai.repository.IncidentAnalysisRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class IncidentAnalysisPersistenceService {

    private final IncidentAnalysisRepository repository;

    public IncidentAnalysisPersistenceService(
            IncidentAnalysisRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Optional<IncidentAnalysis> findExisting(UUID incidentId) {
        return repository.findByIncident_IncidentId(incidentId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public IncidentAnalysis saveAnalysis(
            Incident incident,
            String analysis,
            Instant analyzedAt) {

        IncidentAnalysis newAnalysis = new IncidentAnalysis(
                incident,
                analysis,
                "COMPLETED",
                analyzedAt
        );

        // Flush here so a unique-constraint violation occurs
        // inside this method, where it can be handled by the caller.
        return repository.saveAndFlush(newAnalysis);
    }
}
