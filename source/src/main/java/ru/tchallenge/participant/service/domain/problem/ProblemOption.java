package ru.tchallenge.participant.service.domain.problem;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ProblemOption {

    private final String textcode;
    private final Integer content;
    private final Boolean correct;
}
