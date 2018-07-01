package ru.tchallenge.pilot.service.domain.problem.snippet;

import ru.tchallenge.pilot.service.utility.data.GenericProjector;

public final class ProblemSnippetProjector extends GenericProjector {

    public static final ProblemSnippetProjector INSTANCE = new ProblemSnippetProjector();

    private ProblemSnippetProjector() {

    }

    public ProblemSnippet problemSnippet(final ProblemSnippetDocument document) {
        return ProblemSnippet.builder()
                .content(document.getContent())
                .style(document.getStyle())
                .build();
    }
}
