package ru.tchallenge.participant.service.security.token;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;

@Data
@Builder
public class SecurityToken {

    private String id;
    private String email;
    private String payload;
    private OffsetDateTime createdAt;
    private OffsetDateTime validUntil;

    public boolean isExpired() {
        return validUntil.isBefore(OffsetDateTime.now());
    }

    public void prolongate(Duration duration) {
        validUntil = validUntil.plus(duration);
    }
}
