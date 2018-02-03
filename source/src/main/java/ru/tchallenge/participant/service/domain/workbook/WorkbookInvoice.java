package ru.tchallenge.participant.service.domain.workbook;

import lombok.Builder;
import lombok.Data;
import ru.tchallenge.participant.service.domain.maturity.Maturity;
import ru.tchallenge.participant.service.utility.data.Id;
import ru.tchallenge.participant.service.utility.validation.ValidationAware;

import java.util.Collection;

@Data
@Builder
public final class WorkbookInvoice implements ValidationAware {

    private Id eventId;
    private Maturity maturity;
    private Id specializationId;

    @Override
    public void registerViolations(final Collection<String> violations) {
        if (eventId == null) {
            violations.add("Event ID is missing");
        }
        if (maturity == null) {
            violations.add("Maturity is missing");
        }
        if (specializationId == null) {
            violations.add("Specialization ID is missing");
        }
    }
}
