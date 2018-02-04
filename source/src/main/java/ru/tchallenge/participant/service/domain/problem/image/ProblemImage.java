package ru.tchallenge.participant.service.domain.problem.image;

import lombok.Builder;
import lombok.Data;

import ru.tchallenge.participant.service.utility.data.Id;

@Data
@Builder
public final class ProblemImage {

    private final Id binaryId;
    private final ProblemImageFormat format;
    private final Integer height;
    private final Integer width;
}
