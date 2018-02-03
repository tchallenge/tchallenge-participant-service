package ru.tchallenge.participant.service.domain.problem;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

public final class ProblemManager {

    public static final ProblemManager INSTANCE = new ProblemManager();

    public ProblemRandomResult retrieveRandomResult(final ProblemRandomInvoice invoice) {
        final List<Problem> random = problemRepository
                .findRandom(invoice)
                .map(problemProjector::intoProblemRandomResultItem)
                .into(Lists.newArrayList());
        return ProblemRandomResult.builder()
                .items(ImmutableList.copyOf(random))
                .build();
    }

    private final ProblemProjector problemProjector = ProblemProjector.INSTANCE;
    private final ProblemRepository problemRepository = ProblemRepository.INSTANCE;

    private ProblemManager() {

    }
}
