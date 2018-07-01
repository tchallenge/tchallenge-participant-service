package ru.tchallenge.pilot.service.domain.problem.snippet;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ProblemSnippet {

    private final String content;
    private final ProblemSnippetStyle style;
}
