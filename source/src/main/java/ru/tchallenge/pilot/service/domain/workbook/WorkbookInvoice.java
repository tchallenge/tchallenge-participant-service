package ru.tchallenge.pilot.service.domain.workbook;

import java.util.Collection;

import lombok.Builder;
import lombok.Data;

import ru.tchallenge.pilot.service.domain.maturity.Maturity;
import ru.tchallenge.pilot.service.utility.data.Id;
import ru.tchallenge.pilot.service.utility.validation.ValidationAware;

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
