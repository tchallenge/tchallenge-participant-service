package ru.tchallenge.participant.service.domain.workbook;

import com.google.common.collect.ImmutableList;
import org.bson.Document;
import ru.tchallenge.participant.service.domain.workbook.assignment.Assignment;
import ru.tchallenge.participant.service.domain.workbook.assignment.AssignmentProjector;
import ru.tchallenge.participant.service.domain.problem.ProblemRepository;
import ru.tchallenge.participant.service.utility.data.DocumentWrapper;
import ru.tchallenge.participant.service.utility.data.GenericProjector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class WorkbookProjector extends GenericProjector {

    public static final WorkbookProjector INSTANCE = new WorkbookProjector();

    private final AssignmentProjector assignmentProjector = AssignmentProjector.INSTANCE;
    private final ProblemRepository problemRepository = ProblemRepository.INSTANCE;

    private WorkbookProjector() {

    }

    public Workbook workbook(final WorkbookDocument workbookDocument) {
        final WorkbookStatus status = workbookDocument.getStatus();
        final boolean classified = classifiedByStatus(status);
        return Workbook.builder()
                .id(workbookDocument.getId())
                .eventId(workbookDocument.getEventId())
                .specializationId(workbookDocument.getSpecializationId())
                .ownerId(workbookDocument.getOwnerId())
                .maturity(workbookDocument.getMaturity())
               // .assignments(ImmutableList.copyOf(assignments(document, classified)))
                .submittableUntil(workbookDocument.getSubmittableUntil())
                .status(status)
                .build();
    }

    private boolean classifiedByStatus(final WorkbookStatus status) {
        return status != WorkbookStatus.ASSESSED;
    }

    private List<Assignment> assignments(final Document document, final boolean classified) {
        final List<Assignment> result = new ArrayList<>();
        final List<Document> assignmentDocuments = assignmentDocuments(document);
        final List<Document> problemDocuments = problemDocuments(assignmentDocuments);
        for (int i = 0; i < assignmentDocuments.size(); i++) {
            final Document assignmentDocument = assignmentDocuments.get(i);
            final Document problemDocument = problemDocuments.get(i);
            result.add(assignmentProjector.intoAssignment(assignmentDocument, problemDocument, classified));
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private List<Document> assignmentDocuments(final Document workbookDocument) {
        return (List<Document>) workbookDocument.get("assignments");
    }

    private List<Document> problemDocuments(final List<Document> assignmentDocuments) {
        final List<String> ids = assignmentDocuments
                .stream()
                .map(a -> a.getObjectId("problemId").toHexString())
                .collect(Collectors.toList());
        // TODO: extend problem repository with the required method
        // return problemRepository.findByIds(ids);
        throw new UnsupportedOperationException();
    }
}
