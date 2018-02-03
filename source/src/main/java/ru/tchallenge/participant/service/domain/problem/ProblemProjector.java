package ru.tchallenge.participant.service.domain.problem;

import org.bson.Document;
import ru.tchallenge.participant.service.utility.data.DocumentWrapper;
import ru.tchallenge.participant.service.utility.data.GenericProjector;

public final class ProblemProjector extends GenericProjector {

    public static final ProblemProjector INSTANCE = new ProblemProjector();

    private ProblemProjector() {

    }

    public Problem intoProblem(final Document document, final boolean classified) {
        return Problem.builder()
                .id(new DocumentWrapper(document).getId().toHex())
                .build();
    }

    public Problem intoProblemRandomResultItem(final Document document) {
        return Problem.builder()
                .id(new DocumentWrapper(document).getId().toHex())
                .build();
    }
}
