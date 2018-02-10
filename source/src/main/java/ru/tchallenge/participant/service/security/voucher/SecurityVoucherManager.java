package ru.tchallenge.participant.service.security.voucher;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class SecurityVoucherManager {

    public static final SecurityVoucherManager INSTANCE = new SecurityVoucherManager();

    private final Map<String, SecurityVoucher> vouchers = new HashMap<>();

    private SecurityVoucherManager() {

    }

    public SecurityVoucher create(final String email) {
        final SecurityVoucher voucher = SecurityVoucher.builder()
                .id(UUID.randomUUID().toString())
                .accountEmail(email)
                .payload(UUID.randomUUID().toString())
                .createdAt(Instant.now())
                .validUntil(Instant.now().plusSeconds(300))
                .build();
        vouchers.put(voucher.getPayload(), voucher);
        return voucher;
    }

    public SecurityVoucher utilizeByPayload(final String payload) {
        final SecurityVoucher voucher = vouchers.get(payload);
        if (voucher == null || voucher.isExpired()) {
            return null;
        }
        vouchers.remove(payload);
        return voucher;
    }
}
