package ru.tchallenge.pilot.service.domain.account;

import lombok.Builder;
import lombok.Data;

import ru.tchallenge.pilot.service.utility.validation.ValidationAware;

import java.util.Collection;

@Data
@Builder
public final class AccountInvoice implements ValidationAware {

    private String email;
    private String password;
    private AccountPersonality personality;

    @Override
    public void registerViolations(final Collection<String> violations) {
        if (email == null || email.isEmpty()) {
            violations.add("Account email is invalid");
        }
        if (password == null || password.isEmpty()) {
            violations.add("Account password is invalid");
        }
    }
}
