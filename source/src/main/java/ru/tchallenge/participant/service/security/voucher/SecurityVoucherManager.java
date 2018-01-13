package ru.tchallenge.participant.service.security.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class SecurityVoucherManager {

    public static String create(SecurityVoucherInvoice invoice) {
        SecurityVoucher voucher = SecurityVoucher.builder()
                .id(UUID.randomUUID().toString())
                .backlink(invoice.getBacklink())
                .email(invoice.getEmail())
                .payload(UUID.randomUUID().toString())
                .createdAt(OffsetDateTime.now())
                .validUntil(OffsetDateTime.now().plusMinutes(15))
                .build();
        VOUCHERS.put(voucher.getPayload(), voucher);
        sendViaEmail(voucher);
        return voucher.getPayload();
    }

    public static SecurityVoucher utilize(String payload) {
        SecurityVoucher voucher = VOUCHERS.get(payload);
        if (voucher == null || voucher.isExpired()) {
            throw new RuntimeException("Security voucher is expired or does not exist");
        }
        VOUCHERS.remove(payload);
        return voucher;
    }

    private static void sendViaEmail(SecurityVoucher voucher) {
        LOG.info("A new security voucher {} is created", voucher.getId());
    }

    private static final Logger LOG = LoggerFactory.getLogger(SecurityVoucherManager.class);
    private static final Map<String, SecurityVoucher> VOUCHERS = new HashMap<>();

    private SecurityVoucherManager() {

    }
}
