package ru.tchallenge.participant.service.domain.workbook;

import lombok.Builder;
import lombok.Data;
import ru.tchallenge.participant.service.utility.validation.ValidationAware;

import java.util.Collection;

@Data
@Builder
public final class WorkbookInvoice implements ValidationAware {

    private String eventId;
    private String specializationId;
    private String maturity;

    @Override
    public void registerViolations(final Collection<String> violations) {

    }
}
