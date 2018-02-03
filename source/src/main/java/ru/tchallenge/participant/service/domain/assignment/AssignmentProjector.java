package ru.tchallenge.participant.service.domain.assignment;

import org.bson.Document;
import ru.tchallenge.participant.service.domain.problem.ProblemProjector;

public final class AssignmentProjector {

    public static final AssignmentProjector INSTANCE = new AssignmentProjector();

    private final ProblemProjector problemProjector = ProblemProjector.INSTANCE;

    private AssignmentProjector() {

    }

    public Assignment intoAssignment(final Document assignmentDocument,
                                     final Document problemDocument,
                                     final boolean classified) {
        return Assignment.builder()
                .id(assignmentDocument.getObjectId("_id").toHexString())
                .problem(problemProjector.intoProblem(problemDocument, classified))
                .score((assignmentDocument.getInteger("score")))
                .scoreMax((assignmentDocument.getInteger("scoreMax")))
                .solution(assignmentDocument.getString("solution"))
                .status(assignmentDocument.getString("status"))
                .build();
    }
}
