package ru.tchallenge.participant.service.domain.workbook;

import lombok.Builder;
import lombok.Data;
import ru.tchallenge.participant.service.utility.validation.ValidationAware;

import java.util.Collection;

@Data
@Builder
public final class WorkbookUpdateInvoice implements ValidationAware {

    private String status;

    @Override
    public void registerViolations(final Collection<String> violations) {

    }
}
