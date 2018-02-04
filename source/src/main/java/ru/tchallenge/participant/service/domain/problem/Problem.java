package ru.tchallenge.participant.service.domain.problem;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Data;
import ru.tchallenge.participant.service.utility.data.Id;
import ru.tchallenge.participant.service.utility.data.IdAware;

@Data
@Builder
public final class Problem implements IdAware {

    private final Id id;
    private final ImmutableList<ProblemCategory> categories;
    private final Integer complexity;
    private final ProblemDifficulty difficulty;
    private final ProblemExpectation expectation;
    private final String introduction;
    private final String question;
    private final ImmutableList<ProblemImage> images;
    private final ImmutableList<ProblemSnippet> snippets;
    private final ImmutableList<ProblemOption> options;
}
