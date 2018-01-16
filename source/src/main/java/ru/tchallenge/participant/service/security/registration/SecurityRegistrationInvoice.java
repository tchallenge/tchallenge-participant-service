package ru.tchallenge.participant.service.security.registration;

import lombok.Builder;
import lombok.Data;
import ru.tchallenge.participant.service.utility.validation.ValidationAware;

import java.util.Collection;

@Data
@Builder
public final class SecurityRegistrationInvoice implements ValidationAware {

    private String backlink;
    private String email;
    private String password;
    private String quickname;

    @Override
    public void registerViolations(final Collection<String> violations) {
        if (email == null || email.isEmpty()) {
            violations.add("Email is invalid");
        }
        if (backlink == null || backlink.isEmpty()) {
            violations.add("Back link is invalid");
        }
        if (password == null || password.isEmpty()) {
            violations.add("Password is invalid");
        }
    }
}
