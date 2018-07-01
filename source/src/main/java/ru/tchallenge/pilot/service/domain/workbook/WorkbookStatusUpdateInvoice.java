package ru.tchallenge.pilot.service.domain.workbook;

import java.util.Collection;

import lombok.Builder;
import lombok.Data;

import ru.tchallenge.pilot.service.utility.validation.ValidationAware;

@Data
@Builder
public final class WorkbookStatusUpdateInvoice implements ValidationAware {

    private WorkbookStatus status;

    @Override
    public void registerViolations(final Collection<String> violations) {
        if (status == null) {
            violations.add("status is missing");
        }
    }
}
