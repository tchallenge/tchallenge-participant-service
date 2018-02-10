package ru.tchallenge.participant.service.security.token;

import java.time.Duration;
import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class SecurityToken {

    private final String id;
    private final String accountId;
    private final String payload;
    private final Instant createdAt;
    private Instant validUntil;

    public boolean isExpired() {
        return validUntil.isBefore(Instant.now());
    }

    public void prolongate(final Duration duration) {
        validUntil = validUntil.plus(duration);
    }
}
