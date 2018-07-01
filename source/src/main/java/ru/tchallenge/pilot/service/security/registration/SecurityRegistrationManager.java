package ru.tchallenge.pilot.service.security.registration;

import java.util.UUID;

import ru.tchallenge.pilot.service.domain.account.AccountInvoice;
import ru.tchallenge.pilot.service.domain.account.AccountPersonality;
import ru.tchallenge.pilot.service.domain.account.AccountSystemManager;

public final class SecurityRegistrationManager {

    public static final SecurityRegistrationManager INSTANCE = new SecurityRegistrationManager();

    private final AccountSystemManager accountSystemManager = AccountSystemManager.INSTANCE;

    private SecurityRegistrationManager() {

    }

    public SecurityRegistration create(final SecurityRegistrationInvoice invoice) {
        final AccountInvoice accountInvoice = AccountInvoice.builder()
                .email(invoice.getEmail())
                .password(invoice.getPassword())
                .personality(AccountPersonality.builder()
                        .quickname(invoice.getQuickname())
                        .build())
                .build();
        accountSystemManager.create(accountInvoice);
        return SecurityRegistration.builder()
                .id(UUID.randomUUID().toString())
                .build();
    }
}
