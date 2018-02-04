package ru.tchallenge.participant.service.domain.problem.option;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ProblemOption {

    private final String content;
    private final Boolean correct;
}
