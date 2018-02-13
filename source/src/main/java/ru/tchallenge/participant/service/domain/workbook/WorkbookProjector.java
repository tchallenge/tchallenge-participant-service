package ru.tchallenge.participant.service.domain.workbook;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.Document;

import ru.tchallenge.participant.service.domain.problem.ProblemDocument;
import ru.tchallenge.participant.service.domain.problem.ProblemRepository;
import ru.tchallenge.participant.service.domain.workbook.assignment.Assignment;
import ru.tchallenge.participant.service.domain.workbook.assignment.AssignmentDocument;
import ru.tchallenge.participant.service.domain.workbook.assignment.AssignmentProjector;
import ru.tchallenge.participant.service.utility.data.GenericProjector;
import ru.tchallenge.participant.service.utility.data.Id;

public final class WorkbookProjector extends GenericProjector {

    public static final WorkbookProjector INSTANCE = new WorkbookProjector();

    private final AssignmentProjector assignmentProjector = AssignmentProjector.INSTANCE;
    private final ProblemRepository problemRepository = ProblemRepository.INSTANCE;

    private WorkbookProjector() {

    }

    public Workbook workbook(final WorkbookDocument document) {
        final WorkbookStatus status = document.getStatus();
        final boolean classified = classifiedByStatus(status);
        return Workbook.builder()
                .id(document.getId())
                .eventId(document.getEventId())
                .specializationId(document.getSpecializationId())
                .ownerId(document.getOwnerId())
                .maturity(document.getMaturity())
                .assignments(immutableList(assignments(document, classified)))
                .submittableUntil(document.getSubmittableUntil())
                .status(status)
                .build();
    }

    private boolean classifiedByStatus(final WorkbookStatus status) {
        return status != WorkbookStatus.ASSESSED;
    }

    private List<Assignment> assignments(final WorkbookDocument document, final boolean classified) {
        final List<Assignment> result = new ArrayList<>();
        final List<AssignmentDocument> assignmentDocuments = document.getAssignments();
        final Map<Id, ProblemDocument> problemDocuments = problemDocuments(assignmentDocuments);
        final Indexer indexer = new Indexer();
        for (final AssignmentDocument assignmentDocument : assignmentDocuments) {
            final ProblemDocument problemDocument = problemDocuments.get(assignmentDocument.getProblemId());
            final Assignment assignment = assignmentProjector.assignment(assignmentDocument, problemDocument, classified, indexer.inc());
            result.add(assignment);
        }
        return result;
    }

    private static final class Indexer {

        private int index = 0;

        public int inc() {
            return ++index;
        }
    }

    @SuppressWarnings("unchecked")
    private List<Document> assignmentDocuments(final Document workbookDocument) {
        return (List<Document>) workbookDocument.get("assignments");
    }

    private Map<Id, ProblemDocument> problemDocuments(final List<AssignmentDocument> assignmentDocuments) {
        final List<Id> ids = assignmentDocuments
                .stream()
                .map(AssignmentDocument::getProblemId)
                .collect(Collectors.toList());
        return problemRepository
                .findByIds(ids)
                .stream()
                .collect(Collectors.toMap(ProblemDocument::getId, p -> p));
    }
}
