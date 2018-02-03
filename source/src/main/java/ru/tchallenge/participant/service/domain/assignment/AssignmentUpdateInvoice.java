package ru.tchallenge.participant.service.domain.assignment;

import lombok.Builder;
import lombok.Data;
import ru.tchallenge.participant.service.utility.validation.ValidationAware;

import java.util.Collection;

@Data
@Builder
public final class AssignmentUpdateInvoice implements ValidationAware {

    private String solution;

    @Override
    public void registerViolations(final Collection<String> violations) {

    }
}
