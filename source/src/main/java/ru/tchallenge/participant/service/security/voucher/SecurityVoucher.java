package ru.tchallenge.participant.service.security.voucher;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class SecurityVoucher {

    private final String id;
    private final String accountEmail;
    private final String payload;
    private final Instant createdAt;
    private final Instant validUntil;

    public boolean isExpired() {
        return validUntil.isBefore(Instant.now());
    }
}
