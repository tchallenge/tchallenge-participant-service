package ru.tchallenge.pilot.service.domain.problem;

import java.util.List;

import spark.RouteGroup;
import static spark.Spark.*;

import ru.tchallenge.pilot.service.context.GenericApplicationComponent;
import ru.tchallenge.pilot.service.context.ManagedComponent;
import ru.tchallenge.pilot.service.utility.data.IdAware;
import ru.tchallenge.pilot.service.utility.serialization.Json;

@ManagedComponent
public class ProblemRouter extends GenericApplicationComponent implements RouteGroup {

    private ProblemFacade problemFacade;

    @Override
    public void init() {
        super.init();
        this.problemFacade = getComponent(ProblemFacade.class);
    }

    @Override
    public void addRoutes() {
        path("/problems", () -> {
            get("/", (request, response) -> {
                final List<Problem> problems = problemFacade.retrieveAll(request);
                return Json.json(problems, response);
            });
            post("/", (request, response) -> {
                final ProblemInvoice invoice = Json.body(ProblemInvoice.class, request);
                final IdAware idAware = problemFacade.create(request, invoice);
                return Json.json(idAware.justId(), response);
            });
            post("/random", (request, response) -> {
                final ProblemRandomInvoice invoice = Json.body(ProblemRandomInvoice.class, request);
                final List<Problem> problems = problemFacade.retrieveRandom(request, invoice);
                return Json.json(problems, response);
            });
        });
    }
}
