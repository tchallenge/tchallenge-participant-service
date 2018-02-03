package ru.tchallenge.participant.service.domain.workbook;

import org.bson.types.ObjectId;
import ru.tchallenge.participant.service.domain.maturity.Maturity;
import ru.tchallenge.participant.service.domain.problem.ProblemRepository;
import ru.tchallenge.participant.service.domain.workbook.assignment.AssignmentUpdateInvoice;
import ru.tchallenge.participant.service.security.authentication.AuthenticationContext;
import ru.tchallenge.participant.service.utility.data.IdAware;
import ru.tchallenge.participant.service.utility.data.Id;

import java.time.Duration;
import java.time.Instant;

public final class WorkbookManager {

    public static WorkbookManager INSTANCE = new WorkbookManager();

    private final ProblemRepository problemRepository = ProblemRepository.INSTANCE;
    private final WorkbookProjector workbookProjector = WorkbookProjector.INSTANCE;
    private final WorkbookRepository workbookRepository = WorkbookRepository.INSTANCE;

    private WorkbookManager() {

    }

    public IdAware create(final WorkbookInvoice invoice) {
        final WorkbookDocument workbookDocument = prepareNewWorkbook(invoice);
        workbookRepository.insert(workbookDocument);
        return workbookDocument.justId();
    }

    private WorkbookDocument prepareNewWorkbook(final WorkbookInvoice invoice) {
        final String accountId = AuthenticationContext.getAuthentication().getAccountId();
        final Id ownerId = new Id(accountId);
        final Id eventId = invoice.getEventId();
        final Id specializationId = invoice.getSpecializationId();
        final Maturity maturity = invoice.getMaturity();
        return new WorkbookDocument()
                .ownerId(ownerId)
                .eventId(eventId)
                .specializationId(specializationId)
                .maturity(maturity)
                .submittableUntil(Instant.now().plus(Duration.ofHours(6)))
                .status(WorkbookStatus.APPROVED);
    }

    public Workbook get(final String id) {
        throw new UnsupportedOperationException();
    }

    public void updateAssignment(final String id, final Integer index, final AssignmentUpdateInvoice invoice) {
        throw new UnsupportedOperationException();
    }

    public void updateStatus(final String id, final WorkbookStatusUpdateInvoice invoice) {
        throw new UnsupportedOperationException();
    }
}
