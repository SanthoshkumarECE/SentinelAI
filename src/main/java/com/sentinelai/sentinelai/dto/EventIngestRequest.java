
package com.sentinelai.sentinelai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EventIngestRequest(

        @NotBlank(message = "Service name is required")
        @Size(max = 100, message = "Service name must not exceed 100 characters")
        String serviceName,

        @NotBlank(message = "Log level is required")
        @Pattern(
                regexp = "ERROR|WARN|INFO",
                message = "Log level must be ERROR, WARN, or INFO"
        )
        String level,

        @NotBlank(message = "Event message is required")
        @Size(max = 2000, message = "Message must not exceed 2000 characters")
        String message,

        @Size(max = 100, message = "Trace ID must not exceed 100 characters")
        String traceId
) {
}
