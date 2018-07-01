package ru.tchallenge.pilot.service.domain.account;

import lombok.Builder;
import lombok.Data;

import ru.tchallenge.pilot.service.utility.validation.ValidationAware;

import java.util.Collection;

@Data
@Builder
public class AccountPasswordUpdateInvoice implements ValidationAware {

    private String current;
    private String desired;

    @Override
    public void registerViolations(final Collection<String> violations) {
        if (current == null) {
            violations.add("Current password is missing");
        }
        if (desired == null || desired.isEmpty()) {
            violations.add("Account password is invalid");
        }
    }
}
