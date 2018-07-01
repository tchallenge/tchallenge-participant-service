package ru.tchallenge.pilot.service.domain.workbook.assignment;

import ru.tchallenge.pilot.service.context.ManagedComponent;
import ru.tchallenge.pilot.service.domain.problem.ProblemDocument;
import ru.tchallenge.pilot.service.domain.problem.ProblemProjector;
import ru.tchallenge.pilot.service.utility.data.GenericProjector;

@ManagedComponent
public class AssignmentProjector extends GenericProjector {

    private ProblemProjector problemProjector;

    @Override
    public void init() {
        super.init();
        this.problemProjector = getComponent(ProblemProjector.class);
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
