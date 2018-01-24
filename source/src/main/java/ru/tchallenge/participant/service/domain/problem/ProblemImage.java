package ru.tchallenge.participant.service.domain.problem;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ProblemImage {

    private final String textcode;
    private final String binaryId;
    private final String format;
    private final Integer height;
    private final Integer width;
}
