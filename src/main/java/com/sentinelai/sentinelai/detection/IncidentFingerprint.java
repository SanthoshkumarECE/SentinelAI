
package com.sentinelai.sentinelai.detection;

import com.sentinelai.sentinelai.dto.EventIngestRequest;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Locale;

public final class IncidentFingerprint {

    private IncidentFingerprint() {
    }

    public static String generate(EventIngestRequest request) {
        String normalizedMessage = request.message()
                .trim()
                .replaceAll("\\s+", " ")
                .toLowerCase(Locale.ROOT);

        String input = request.serviceName().trim().toLowerCase(Locale.ROOT)
                + "|" + request.level().trim().toUpperCase(Locale.ROOT)
                + "|" + normalizedMessage;

        try {
            byte[] hash = MessageDigest.getInstance("SHA-256")
                    .digest(input.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException(
                    "SHA-256 algorithm is unavailable", exception);
        }
    }
}
