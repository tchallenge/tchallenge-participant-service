package ru.tchallenge.participant.service.domain.workbook.assignment;

import ru.tchallenge.participant.service.domain.problem.ProblemDocument;
import ru.tchallenge.participant.service.domain.problem.ProblemProjector;
import ru.tchallenge.participant.service.utility.data.GenericProjector;

public final class AssignmentProjector extends GenericProjector {

    public static final AssignmentProjector INSTANCE = new AssignmentProjector();

    private final ProblemProjector problemProjector = ProblemProjector.INSTANCE;

    private AssignmentProjector() {

    }

    public Assignment assignment(final AssignmentDocument assignmentDocument,
                                 final ProblemDocument problemDocument,
                                 final boolean classified,
                                 final int index) {
        return Assignment.builder()
                .index(index)
                .problem(problemProjector.problem(problemDocument, classified))
                .score(assignmentDocument.getScore())
                .scoreMax(assignmentDocument.getScoreMax())
                .solution(assignmentDocument.getSolution())
                .build();
    }
}
