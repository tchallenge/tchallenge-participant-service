package ru.tchallenge.participant.service.domain.assignment;

import lombok.Builder;
import lombok.Data;
import ru.tchallenge.participant.service.domain.problem.Problem;
import ru.tchallenge.participant.service.utility.data.IdAware;

@Data
@Builder
public final class Assignment implements IdAware {

    private final String id;
    private final Problem problem;
    private final Integer score;
    private final Integer scoreMax;
    private final String solution;
    private final String status;
}
