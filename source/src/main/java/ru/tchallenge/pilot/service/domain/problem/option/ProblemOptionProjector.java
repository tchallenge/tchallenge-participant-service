package ru.tchallenge.pilot.service.domain.problem.option;

import ru.tchallenge.pilot.service.context.ManagedComponent;
import ru.tchallenge.pilot.service.utility.data.GenericProjector;

@ManagedComponent
public class ProblemOptionProjector extends GenericProjector {

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
