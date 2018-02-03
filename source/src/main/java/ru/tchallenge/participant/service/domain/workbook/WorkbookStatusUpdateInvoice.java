package ru.tchallenge.participant.service.domain.workbook;

import lombok.Builder;
import lombok.Data;
import ru.tchallenge.participant.service.utility.validation.ValidationAware;

import java.util.Collection;

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
