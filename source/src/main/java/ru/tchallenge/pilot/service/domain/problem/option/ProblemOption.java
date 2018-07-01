package ru.tchallenge.pilot.service.domain.problem.option;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ProblemOption {

    private final Integer index;
    private final String textcode;
    private final String content;
    private final Boolean correct;
}
