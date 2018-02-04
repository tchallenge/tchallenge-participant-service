package ru.tchallenge.participant.service.domain.workbook.assignment;

import java.util.Collection;

import lombok.Builder;
import lombok.Data;

import ru.tchallenge.participant.service.utility.validation.ValidationAware;

@Data
@Builder
public final class AssignmentUpdateInvoice implements ValidationAware {

    private String solution;

    @Override
    public void registerViolations(final Collection<String> violations) {

    }
}
