package ru.tchallenge.participant.service.domain.problem;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class Problem {

    private final String id;
    private final ImmutableList<String> categories;
    private final Integer complexity;
    private final String difficulty;
    private final String expectation;
    private final String introduction;
    private final String question;
    private final ImmutableList<ProblemImage> images;
    private final ImmutableList<ProblemSnippet> snippets;
    private final ImmutableList<ProblemOption> options;
    private final String solution;
}
