package ru.tchallenge.participant.service.domain.problem;

import org.bson.Document;

public final class ProblemProjector {

    public static final ProblemProjector INSTANCE = new ProblemProjector();

    private ProblemProjector() {

    }

    public Problem intoProblem(final Document document, final boolean classified) {
        return Problem.builder()
                .id(document.getObjectId("_id").toHexString())
                .build();
    }

    public Problem intoProblemRandomResultItem(final Document document) {
        return Problem.builder()
                .id(document.getObjectId("_id").toHexString())
                .build();
    }
}
