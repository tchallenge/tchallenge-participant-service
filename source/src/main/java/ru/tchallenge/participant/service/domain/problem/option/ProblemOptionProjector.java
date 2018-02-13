package ru.tchallenge.participant.service.domain.problem.option;

import ru.tchallenge.participant.service.utility.data.GenericProjector;

public final class ProblemOptionProjector extends GenericProjector {

    public static final ProblemOptionProjector INSTANCE = new ProblemOptionProjector();

    private ProblemOptionProjector() {

    }

    public ProblemOption problemOption(final ProblemOptionDocument document,
                                       final boolean classified,
                                       final int index) {
        return ProblemOption.builder()
                .index(index)
                .textcode(String.valueOf( (char) (64 + index)))
                .content(document.getContent())
                .correct(classified ? null : document.getCorrect())
                .build();
    }
}
