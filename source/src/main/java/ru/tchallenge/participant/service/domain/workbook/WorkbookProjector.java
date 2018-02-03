package ru.tchallenge.participant.service.domain.workbook;

import com.google.common.collect.ImmutableList;
import org.bson.Document;
import ru.tchallenge.participant.service.domain.assignment.Assignment;
import ru.tchallenge.participant.service.domain.assignment.AssignmentProjector;
import ru.tchallenge.participant.service.domain.problem.ProblemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class WorkbookProjector {

    public static final WorkbookProjector INSTANCE = new WorkbookProjector();

    private final AssignmentProjector assignmentProjector = AssignmentProjector.INSTANCE;
    private final ProblemRepository problemRepository = ProblemRepository.INSTANCE;

    private WorkbookProjector() {

    }

    public Workbook workbook(final Document document) {
        final String status = document.getString("status");
        final boolean classified = classifiedByStatus(status);
        return Workbook.builder()
                .id(document.getObjectId("_id").toHexString())
                .eventId(document.getObjectId("eventId").toHexString())
                .ownerId(document.getObjectId("ownerId").toHexString())
                .assignments(ImmutableList.copyOf(assignments(document, classified)))
                .submittableUntil(document.getDate("submittableUntil").toInstant())
                .status(status)
                .build();
    }

    private boolean classifiedByStatus(final String status) {
        return !status.equals("ASSESSED");
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
