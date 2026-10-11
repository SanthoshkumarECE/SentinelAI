
package com.sentinelai.sentinelai.exception;

import java.util.UUID;

public class IncidentNotFoundException extends RuntimeException {

    public IncidentNotFoundException(UUID incidentId) {
        super("Incident not found with ID: " + incidentId);
    }
}

