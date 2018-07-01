package ru.tchallenge.pilot.service.domain.problem.snippet;

import ru.tchallenge.pilot.service.context.ManagedComponent;
import ru.tchallenge.pilot.service.utility.data.GenericProjector;

@ManagedComponent
public class ProblemSnippetProjector extends GenericProjector {

    public ProblemSnippet problemSnippet(final ProblemSnippetDocument document) {
        return ProblemSnippet.builder()
                .content(document.getContent())
                .style(document.getStyle())
                .build();
    }
}
