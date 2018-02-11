package ru.tchallenge.participant.service.domain.problem;

import java.util.List;

import ru.tchallenge.participant.service.utility.data.IdAware;
import ru.tchallenge.participant.service.utility.experimental.ExperimentalContext;

public final class ProblemFacade {

    public static final ProblemFacade INSTANCE = new ProblemFacade();

    private final ExperimentalContext experimentalContext = ExperimentalContext.INSTANCE;
    private final ProblemManager problemManager = ProblemManager.INSTANCE;

    private ProblemFacade() {

    }

    public IdAware create(final ProblemInvoice invoice) {
        ensureExperimentalFeaturesEnabled();
        return problemManager.create(invoice);
    }

    public List<Problem> retrieveAll() {
        ensureExperimentalFeaturesEnabled();
        return problemManager.retrieveAll();
    }

    public List<Problem> retrieveRandom(final ProblemRandomInvoice invoice) {
        ensureExperimentalFeaturesEnabled();
        return problemManager.retrieveRandom(invoice);
    }

    private void ensureExperimentalFeaturesEnabled() {
        experimentalContext.ensureExperimentalFeaturesEnabled();
    }
}
