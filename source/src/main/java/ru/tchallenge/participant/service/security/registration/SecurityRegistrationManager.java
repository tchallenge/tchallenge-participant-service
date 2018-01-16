package ru.tchallenge.participant.service.security.registration;

import ru.tchallenge.participant.service.domain.account.*;

import java.util.UUID;

public final class SecurityRegistrationManager {

    public static SecurityRegistration create(final SecurityRegistrationInvoice invoice) {
        final AccountInvoice accountInvoice = AccountInvoice.builder()
                .email(invoice.getEmail())
                .password(invoice.getPassword())
                .personality(AccountPersonality.builder()
                        .quickname(invoice.getQuickname())
                        .build())
                .build();
        ACCOUNT_SYSTEM_MANAGER.create(accountInvoice);
        // TODO: send a email with voucher
        return SecurityRegistration.builder()
                .id(UUID.randomUUID().toString())
                .build();
    }

    private static final AccountSystemManager ACCOUNT_SYSTEM_MANAGER = AccountSystemManager.INSTANCE;

    private SecurityRegistrationManager() {

    }
}
