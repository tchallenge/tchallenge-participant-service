package ru.tchallenge.participant.service.domain.problem;

import java.util.List;
import java.util.stream.Collectors;

public final class ProblemManager {

    public static final ProblemManager INSTANCE = new ProblemManager();

    private final ProblemProjector problemProjector = ProblemProjector.INSTANCE;
    private final ProblemRepository problemRepository = ProblemRepository.INSTANCE;

    private ProblemManager() {

    }

    public List<Problem> retrieveAll() {
        return problemRepository
                .findRandom(null)
                .stream()
                .map(d -> problemProjector.problem(d, true))
                .collect(Collectors.toList());
    }
}
