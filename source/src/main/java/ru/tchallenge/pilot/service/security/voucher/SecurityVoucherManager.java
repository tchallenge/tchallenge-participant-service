package ru.tchallenge.pilot.service.security.voucher;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import ru.tchallenge.pilot.service.context.GenericApplicationComponent;
import ru.tchallenge.pilot.service.context.ManagedComponent;

@ManagedComponent
public class SecurityVoucherManager extends GenericApplicationComponent {

    private Map<String, SecurityVoucher> vouchers = new HashMap<>();

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
