
package com.sentinelai.sentinelai.repository;

import com.sentinelai.sentinelai.entity.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IncidentRepository
        extends JpaRepository<Incident, UUID> {

    Optional<Incident> findFirstByFingerprintAndStatus(
            String fingerprint, String status);
}

