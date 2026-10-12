package com.sentinelai.sentinelai.repository;

import com.sentinelai.sentinelai.entity.IncidentAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IncidentAnalysisRepository
        extends JpaRepository<IncidentAnalysis, UUID> {

    Optional<IncidentAnalysis> findByIncident_IncidentId(UUID incidentId);
}
