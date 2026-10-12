package com.sentinelai.sentinelai.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "incident_analyses")
public class IncidentAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "analysis_id", nullable = false, updatable = false)
    private UUID analysisId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incident_id", nullable = false, unique = true)
    private Incident incident;

    @Column(name = "analysis", nullable = false, columnDefinition = "TEXT")
    private String analysis;

    @Column(name = "status", nullable = false, length = 30)
    private String status;

    @Column(name = "analyzed_at", nullable = false)
    private Instant analyzedAt;

    protected IncidentAnalysis() {
    }

    public IncidentAnalysis(
            Incident incident,
            String analysis,
            String status,
            Instant analyzedAt) {
        this.incident = incident;
        this.analysis = analysis;
        this.status = status;
        this.analyzedAt = analyzedAt;
    }

    public UUID getAnalysisId() {
        return analysisId;
    }

    public Incident getIncident() {
        return incident;
    }

    public String getAnalysis() {
        return analysis;
    }

    public String getStatus() {
        return status;
    }

    public Instant getAnalyzedAt() {
        return analyzedAt;
    }

    public void updateAnalysis(
            String analysis,
            String status,
            Instant analyzedAt) {
        this.analysis = analysis;
        this.status = status;
        this.analyzedAt = analyzedAt;
    }
}