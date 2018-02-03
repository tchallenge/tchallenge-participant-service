package ru.tchallenge.participant.service.security.token;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.OffsetDateTime;

@Data
@Builder
public final class SecurityToken {

    private final String id;
    private final String accountId;
    private final String payload;
    private final OffsetDateTime createdAt;
    private OffsetDateTime validUntil;

    public boolean isExpired() {
        return validUntil.isBefore(OffsetDateTime.now());
    }

    public void prolongate(Duration duration) {
        validUntil = validUntil.plus(duration);
    }
}
