package ru.tchallenge.participant.service.domain.workbook.assignment;

import lombok.Builder;
import lombok.Data;
import ru.tchallenge.participant.service.domain.problem.Problem;

@Data
@Builder
public final class Assignment {

    private final Problem problem;
    private final Integer score;
    private final Integer scoreMax;
    private final String solution;
}
