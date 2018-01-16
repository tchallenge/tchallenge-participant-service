package ru.tchallenge.participant.service.security.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class SecurityVoucherManager {

    public static SecurityVoucher create(final SecurityVoucherInvoice invoice) {
        final SecurityVoucher voucher = SecurityVoucher.builder()
                .id(UUID.randomUUID().toString())
                .backlink(invoice.getBacklink())
                .accountEmail(invoice.getEmail())
                .payload(UUID.randomUUID().toString())
                .createdAt(OffsetDateTime.now())
                .validUntil(OffsetDateTime.now().plusMinutes(15))
                .build();
        VOUCHERS.put(voucher.getPayload(), voucher);
        sendViaEmail(voucher);
        return voucher;
    }

    public static SecurityVoucher utilizeByPayload(final String payload) {
        SecurityVoucher voucher = VOUCHERS.get(payload);
        if (voucher == null || voucher.isExpired()) {
            return null;
        }
        VOUCHERS.remove(payload);
        return voucher;
    }

    private static void sendViaEmail(final SecurityVoucher voucher) {
        LOG.info("A new security voucher {} is created", voucher.getId());
    }

    private static final Logger LOG = LoggerFactory.getLogger(SecurityVoucherManager.class);
    private static final Map<String, SecurityVoucher> VOUCHERS = new HashMap<>();

    private SecurityVoucherManager() {

    }
}
