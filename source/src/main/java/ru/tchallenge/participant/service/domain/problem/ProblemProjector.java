package ru.tchallenge.participant.service.domain.problem;

import com.google.common.collect.ImmutableList;
import ru.tchallenge.participant.service.utility.data.GenericProjector;

public final class ProblemProjector extends GenericProjector {

    public static final ProblemProjector INSTANCE = new ProblemProjector();

    private ProblemProjector() {

    }

    public Problem problem(final ProblemDocument document, final boolean classified) {
        return Problem.builder()
                .id(document.getId())
                .complexity(0)
                .categories(ImmutableList.copyOf(document.getCategories()))
                .difficulty(document.getDifficulty())
                .introduction(document.getIntroduction())
                .question(document.getQuestion())
                .expectation(document.getExpectation())
                .build();
    }
}
