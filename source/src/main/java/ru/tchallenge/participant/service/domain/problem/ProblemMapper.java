package ru.tchallenge.participant.service.domain.problem;

import org.bson.Document;

public final class ProblemMapper {

    public static final ProblemMapper INSTANCE = new ProblemMapper();

    public Problem intoProblemRandomResultItem(final Document document) {
        return Problem.builder()
                .id(document.getObjectId("_id").toHexString())
                .build();
    }

    private ProblemMapper() {

    }
}
