package ru.tchallenge.participant.service.security.voucher;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class SecurityVoucher {

    private String id;
    private String backlink;
    private String email;
    private String payload;
    private OffsetDateTime createdAt;
    private OffsetDateTime validUntil;

    public boolean isExpired() {
        return validUntil.isBefore(OffsetDateTime.now());
    }
}
