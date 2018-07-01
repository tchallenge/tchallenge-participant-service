package ru.tchallenge.pilot.service.security.voucher;

import java.util.Collection;

import lombok.Builder;
import lombok.Data;

import ru.tchallenge.pilot.service.utility.validation.ValidationAware;

@Data
@Builder
public final class SecurityVoucherInvoice implements ValidationAware {

    private final String email;
    private final String backlinkTemplate;

    @Override
    public void registerViolations(final Collection<String> violations) {
        if (email == null || email.isEmpty()) {
            violations.add("Email is invalid or missing");
        }
        if (backlinkTemplate == null || backlinkTemplate.isEmpty()) {
            violations.add("Backlink template is invalid or missing");
        }
    }
}
