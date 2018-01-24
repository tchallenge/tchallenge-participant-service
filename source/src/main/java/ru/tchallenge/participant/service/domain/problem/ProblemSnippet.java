package ru.tchallenge.participant.service.domain.problem;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ProblemSnippet {

    private final String textcode;
    private final String content;
    private final String format;
}
