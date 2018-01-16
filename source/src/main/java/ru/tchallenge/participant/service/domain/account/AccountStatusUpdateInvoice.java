package ru.tchallenge.participant.service.domain.account;

import lombok.Builder;
import lombok.Data;
import ru.tchallenge.participant.service.utility.validation.ValidationAware;

import java.util.Collection;

@Data
@Builder
public final class AccountStatusUpdateInvoice implements ValidationAware {

    private String newStatus;

    @Override
    public void registerViolations(final Collection<String> violations) {
        if (newStatus == null || newStatus.isEmpty()) {
            violations.add("Status is missing");
        }
    }
}
