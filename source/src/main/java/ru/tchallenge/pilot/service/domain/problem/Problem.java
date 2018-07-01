package ru.tchallenge.pilot.service.domain.problem;

import lombok.Builder;
import lombok.Data;

import com.google.common.collect.ImmutableList;

import ru.tchallenge.pilot.service.domain.problem.image.ProblemImage;
import ru.tchallenge.pilot.service.domain.problem.option.ProblemOption;
import ru.tchallenge.pilot.service.domain.problem.snippet.ProblemSnippet;
import ru.tchallenge.pilot.service.utility.data.Id;
import ru.tchallenge.pilot.service.utility.data.IdAware;

@Data
@Builder
public final class Problem implements IdAware {

    private final Id id;
    private final ImmutableList<ProblemCategory> categories;
    private final Integer complexity;
    private final ProblemDifficulty difficulty;
    private final ProblemExpectation expectation;
    private final ImmutableList<ProblemImage> images;
    private final String introduction;
    private final ImmutableList<ProblemOption> options;
    private final String question;
    private final ImmutableList<ProblemSnippet> snippets;
    private final ProblemStatus status;
}
