package ru.tchallenge.participant.service.domain.workbook;

import ru.tchallenge.participant.service.utility.data.IdAware;

public final class WorkbookManager {

    public static WorkbookManager INSTANCE = new WorkbookManager();

    private WorkbookManager() {

    }

    public IdAware create(final WorkbookInvoice invoice) {
        throw new UnsupportedOperationException();
    }

    public Workbook get(final String id) {
        throw new UnsupportedOperationException();
    }

    public void updateStatus(final String id, final WorkbookUpdateInvoice invoice) {
        throw new UnsupportedOperationException();
    }
}
