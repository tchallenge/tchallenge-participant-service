package ru.tchallenge.participant.service.domain.assignment;

public final class AssignmentManager {

    public static final AssignmentManager INSTANCE = new AssignmentManager();

    private AssignmentManager() {

    }

    public void updateSolution(final String id, final AssignmentUpdateInvoice invoice) {
        // TODO: implement a call to Workbook repository
        throw new UnsupportedOperationException();
    }
}
