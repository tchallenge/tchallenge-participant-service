package ru.tchallenge.pilot.service.security.registration;

import java.util.Collection;

import lombok.Builder;
import lombok.Data;

import ru.tchallenge.pilot.service.utility.validation.ValidationAware;

@Data
@Builder
public final class SecurityRegistrationInvoice implements ValidationAware {

    private final String email;
    private final String quickname;
    private final String password;
    private final String backlinkTemplate;

    @Override
    public void registerViolations(final Collection<String> violations) {
        if (email == null || email.isEmpty()) {
            violations.add("Email is missing or invalid");
        }
        if (backlinkTemplate == null || backlinkTemplate.isEmpty()) {
            violations.add("Backlink template is missing or invalid");
        }
        if (password == null || password.isEmpty()) {
            violations.add("Password is missing or invalid");
        }
    }
}
