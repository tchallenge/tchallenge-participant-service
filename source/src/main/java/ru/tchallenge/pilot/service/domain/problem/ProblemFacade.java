package ru.tchallenge.pilot.service.domain.problem;

import java.util.List;

import spark.Request;

import ru.tchallenge.pilot.service.context.GenericApplicationComponent;
import ru.tchallenge.pilot.service.context.ManagedComponent;
import ru.tchallenge.pilot.service.utility.data.IdAware;
import ru.tchallenge.pilot.service.utility.experimental.ExperimentalContext;

@ManagedComponent
public class ProblemFacade extends GenericApplicationComponent {

    private ExperimentalContext experimentalContext;
    private ProblemManager problemManager;

    @Override
    public void init() {
        super.init();
        this.experimentalContext = getComponent(ExperimentalContext.class);
        this.problemManager = getComponent(ProblemManager.class);
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
