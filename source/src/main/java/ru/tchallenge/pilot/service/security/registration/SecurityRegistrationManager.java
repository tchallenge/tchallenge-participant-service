package ru.tchallenge.pilot.service.security.registration;

import java.util.UUID;

import ru.tchallenge.pilot.service.context.GenericApplicationComponent;
import ru.tchallenge.pilot.service.context.ManagedComponent;
import ru.tchallenge.pilot.service.domain.account.AccountInvoice;
import ru.tchallenge.pilot.service.domain.account.AccountPersonality;
import ru.tchallenge.pilot.service.domain.account.AccountSystemManager;

@ManagedComponent
public class SecurityRegistrationManager extends GenericApplicationComponent {

    private AccountSystemManager accountSystemManager;

    @Override
    public void init() {
        super.init();
        this.accountSystemManager = getComponent(AccountSystemManager.class);
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
