package com.sentinelai.sentinelai.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID eventId;

    @Column(nullable = false, length = 100)
    private String serviceName;

    @Column(nullable = false, length = 10)
    private String level;

    @Column(nullable = false, length = 2000)
    private String message;

    @Column(length = 100)
    private String traceId;

    @Column(nullable = false, updatable = false)
    private Instant receivedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incident_id")
    private Incident incident;

    protected Event() {}

    public Event(String serviceName, String level,
                 String message, String traceId) {
        this.serviceName = serviceName;
        this.level = level;
        this.message = message;
        this.traceId = traceId;
        this.receivedAt = Instant.now();
    }

    public void assignIncident(Incident incident) {
        this.incident = incident;
    }

    public Incident getIncident() {
        return incident;
    }

    public UUID getEventId() { return eventId; }
    public String getServiceName() { return serviceName; }
    public String getLevel() { return level; }
    public String getMessage() { return message; }
    public String getTraceId() { return traceId; }
    public Instant getReceivedAt() { return receivedAt; }




}