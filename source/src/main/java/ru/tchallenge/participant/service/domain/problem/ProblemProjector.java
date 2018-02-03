package ru.tchallenge.participant.service.domain.problem;

import org.bson.Document;
import ru.tchallenge.participant.service.utility.persistence.DocumentWrapper;
import ru.tchallenge.participant.service.utility.persistence.GenericProjector;

public final class ProblemProjector extends GenericProjector {

    public static final ProblemProjector INSTANCE = new ProblemProjector();

    private ProblemProjector() {

    }

    public Problem intoProblem(final Document document, final boolean classified) {
        return Problem.builder()
                .id(DocumentWrapper.fromDocument(document).getId())
                .build();
    }

    public Problem intoProblemRandomResultItem(final Document document) {
        return Problem.builder()
                .id(DocumentWrapper.fromDocument(document).getId())
                .build();
    }
}
