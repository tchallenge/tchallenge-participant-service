package ru.tchallenge.participant.service.security.voucher;

import lombok.Builder;
import lombok.Data;
import ru.tchallenge.participant.service.utility.validation.ValidationAware;

import java.util.Collection;

@Data
@Builder
public final class SecurityVoucherInvoice implements ValidationAware {

    private String email;
    private String backlink;

    @Override
    public void registerViolations(final Collection<String> violations) {
        if (email == null || email.isEmpty()) {
            violations.add("Email is invalid");
        }
        if (backlink == null || backlink.isEmpty()) {
            violations.add("Back link is invalid");
        }
    }
}
