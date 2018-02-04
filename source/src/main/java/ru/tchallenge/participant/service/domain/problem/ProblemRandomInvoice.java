package ru.tchallenge.participant.service.domain.problem;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public final class ProblemRandomInvoice {

    private Set<ProblemCategory> categories;
    private Set<ProblemDifficulty> difficulties;
    private Integer limit;
}
