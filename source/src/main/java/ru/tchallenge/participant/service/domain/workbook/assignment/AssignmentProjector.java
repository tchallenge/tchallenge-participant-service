package ru.tchallenge.participant.service.domain.workbook.assignment;

import org.bson.Document;
import ru.tchallenge.participant.service.domain.problem.ProblemProjector;
import ru.tchallenge.participant.service.utility.persistence.GenericProjector;

public final class AssignmentProjector extends GenericProjector {

    public static final AssignmentProjector INSTANCE = new AssignmentProjector();

    private final ProblemProjector problemProjector = ProblemProjector.INSTANCE;

    private AssignmentProjector() {

    }

    public Assignment intoAssignment(final Document assignmentDocument,
                                     final Document problemDocument,
                                     final boolean classified) {
        return Assignment.builder()
                .problem(problemProjector.intoProblem(problemDocument, classified))
                .score((assignmentDocument.getInteger("score")))
                .scoreMax((assignmentDocument.getInteger("scoreMax")))
                .solution(assignmentDocument.getString("solution"))
                .build();
    }
}
