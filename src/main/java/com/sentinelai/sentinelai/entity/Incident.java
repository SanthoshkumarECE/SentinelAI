
package com.sentinelai.sentinelai.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "incidents",
        indexes = {
                @Index(name = "idx_incident_fingerprint_status",
                        columnList = "fingerprint, status")
        }
)
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID incidentId;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(nullable = false, length = 64)
    private String fingerprint;

    @Column(nullable = false, length = 20)
    private String severity;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(nullable = false)
    private Instant firstSeen;

    @Column(nullable = false)
    private Instant lastSeen;

    @Column(nullable = false)
    private long occurrenceCount;

    @OneToMany(mappedBy = "incident")
    private List<Event> events = new ArrayList<>();

    protected Incident() {
    }

    public Incident(String title, String fingerprint,
                    String severity, Instant now) {
        this.title = title;
        this.fingerprint = fingerprint;
        this.severity = severity;
        this.status = "OPEN";
        this.firstSeen = now;
        this.lastSeen = now;
        this.occurrenceCount = 0;
    }

    public void recordOccurrence(Instant time) {
        this.lastSeen = time;
        this.occurrenceCount++;
    }

    public UUID getIncidentId() {
        return incidentId;
    }

    public String getTitle() {
        return title;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public String getSeverity() {
        return severity;
    }

    public String getStatus() {
        return status;
    }

    public Instant getFirstSeen() {
        return firstSeen;
    }

    public Instant getLastSeen() {
        return lastSeen;
    }

    public List<Event> getEvents() {
        return events;
    }

    public long getOccurrenceCount() {
        return occurrenceCount;
    }


}
