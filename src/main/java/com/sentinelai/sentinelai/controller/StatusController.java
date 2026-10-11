package com.sentinelai.sentinelai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StatusController {

    @GetMapping("/")
    public Map<String, String> status() {
        return Map.of(
                "application", "SentinelAI",
                "status", "running",
                "message", "Incident analysis service is ready"
        );
    }
}
