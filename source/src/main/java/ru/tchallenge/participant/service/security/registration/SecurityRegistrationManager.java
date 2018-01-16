package ru.tchallenge.participant.service.security.registration;

import ru.tchallenge.participant.service.domain.account.AccountInvoice;
import ru.tchallenge.participant.service.domain.account.AccountManager;
import ru.tchallenge.participant.service.domain.account.AccountPersonality;

import java.util.UUID;

public final class SecurityRegistrationManager {

    public static Object create(final SecurityRegistrationInvoice invoice) {
        AccountInvoice accountInvoice = AccountInvoice.builder()
                .email(invoice.getEmail())
                .password(invoice.getPassword())
                .personality(AccountPersonality.builder()
                        .quickname(invoice.getQuickname())
                        .build())
                .build();
        AccountManager.create(accountInvoice);
        // TODO: send a email with voucher
        return UUID.randomUUID().toString();
    }

    private SecurityRegistrationManager() {

    }
}
