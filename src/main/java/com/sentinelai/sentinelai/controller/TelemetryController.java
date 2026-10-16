package com.sentinelai.sentinelai.controller;

import com.google.protobuf.InvalidProtocolBufferException;
import com.sentinelai.sentinelai.dto.EventIngestRequest;
import com.sentinelai.sentinelai.dto.EventIngestResponse;
import com.sentinelai.sentinelai.dto.TelemetryEventRequest;
import com.sentinelai.sentinelai.service.EventIngestionService;
import io.opentelemetry.proto.trace.v1.ScopeSpans;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.opentelemetry.proto.collector.trace.v1.ExportTraceServiceRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import io.opentelemetry.proto.common.v1.KeyValue;
import io.opentelemetry.proto.trace.v1.Span;
import io.opentelemetry.proto.trace.v1.ResourceSpans;

//@RestController
//@RequestMapping("/api/v1/telemetry")
//public class TelemetryController {
//
//    private final EventIngestionService eventIngestionService;
//
//    public TelemetryController(EventIngestionService eventIngestionService) {
//        this.eventIngestionService = eventIngestionService;
//    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public EventIngestResponse ingestTelemetry(
//            @Valid @RequestBody TelemetryEventRequest telemetry) {
//
//        EventIngestRequest event = new EventIngestRequest(
//                telemetry.serviceName(),
//                telemetry.level(),
//                telemetry.message(),
//                telemetry.traceId()
//        );
//
//        return eventIngestionService.ingest(event);
//    }
//}


@RestController
@RequestMapping("/api/v1/telemetry")
public class TelemetryController {

    private final EventIngestionService eventIngestionService;

    public TelemetryController(EventIngestionService eventIngestionService) {
        this.eventIngestionService = eventIngestionService;
    }

    private String bytesToHex(byte[] bytes) {

        StringBuilder result = new StringBuilder();

        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }

        return result.toString();
    }

    @PostMapping(
            value = "/v1/traces",
            consumes = "application/x-protobuf"
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void ingestTrace(
            @RequestBody byte[] payload,
            HttpServletRequest request) throws IOException {

        System.out.println("Content-Type: " + request.getContentType());
        System.out.println("Content-Encoding: " + request.getHeader("Content-Encoding"));
        System.out.println("Compressed payload size: " + payload.length);

        byte[] protobufBytes;

        if ("gzip".equalsIgnoreCase(request.getHeader("Content-Encoding"))) {

            try (GZIPInputStream gzip =
                         new GZIPInputStream(new ByteArrayInputStream(payload))) {

                protobufBytes = gzip.readAllBytes();
            }

        } else {
            protobufBytes = payload;
        }

        ExportTraceServiceRequest traceRequest =
                ExportTraceServiceRequest.parseFrom(protobufBytes);

        for (ResourceSpans resourceSpans : traceRequest.getResourceSpansList()) {

            // 1. Get service name
            String serviceName = "unknown";

            for (KeyValue attribute :
                    resourceSpans.getResource().getAttributesList()) {

                if (attribute.getKey().equals("service.name")) {
                    serviceName = attribute.getValue().getStringValue();
                }
            }

            System.out.println("Service: " + serviceName);

            // 2. Go through scopes
            for (ScopeSpans scopeSpans :
                    resourceSpans.getScopeSpansList()) {

                // 3. Go through spans
                for (Span span : scopeSpans.getSpansList()) {

                    String traceId =
                            bytesToHex(span.getTraceId().toByteArray());

                    System.out.println("Trace ID: " + traceId);
                    System.out.println("Span: " + span.getName());

                    System.out.println(
                            "Status: " + span.getStatus().getCode()
                    );

                    // 4. Span attributes
                    for (KeyValue attribute :
                            span.getAttributesList()) {

                        System.out.println(
                                attribute.getKey() + " = "
                                        + attribute.getValue()
                        );
                    }

                    // 5. Exceptions
                    for (Span.Event event :
                            span.getEventsList()) {

                        if (event.getName().equals("exception")) {

                            System.out.println(
                                    "EXCEPTION FOUND"
                            );

                            for (KeyValue attribute :
                                    event.getAttributesList()) {

                                System.out.println(
                                        attribute.getKey() + " = "
                                                + attribute.getValue()
                                );
                            }
                        }
                    }
                }
            }
        }

        System.out.println(
                "Received resource spans: "
                        + traceRequest.getResourceSpansCount()
        );
    }
}