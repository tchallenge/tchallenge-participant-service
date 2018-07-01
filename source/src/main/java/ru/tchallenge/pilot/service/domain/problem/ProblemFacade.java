package ru.tchallenge.pilot.service.domain.problem;

import java.util.List;

import spark.Request;

import ru.tchallenge.pilot.service.utility.data.IdAware;
import ru.tchallenge.pilot.service.utility.experimental.ExperimentalContext;

public final class ProblemFacade {

    public static final ProblemFacade INSTANCE = new ProblemFacade();

    private final ExperimentalContext experimentalContext = ExperimentalContext.INSTANCE;
    private final ProblemManager problemManager = ProblemManager.INSTANCE;

    private ProblemFacade() {

    }

    public IdAware create(Request request, ProblemInvoice invoice) {
        ensureExperimentalFeaturesEnabled();
        return problemManager.create(request, invoice);
    }

    public List<Problem> retrieveAll(Request request) {
        ensureExperimentalFeaturesEnabled();
        return problemManager.retrieveAll(request);
    }

    public List<Problem> retrieveRandom(Request request, ProblemRandomInvoice invoice) {
        ensureExperimentalFeaturesEnabled();
        return problemManager.retrieveRandom(request, invoice);
    }

    private void ensureExperimentalFeaturesEnabled() {
        experimentalContext.ensureExperimentalFeaturesEnabled();
    }
}
