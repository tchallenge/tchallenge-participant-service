package ru.tchallenge.pilot.service.domain.problem;

import java.util.List;

import spark.RouteGroup;
import static spark.Spark.*;

import ru.tchallenge.pilot.service.utility.data.IdAware;
import ru.tchallenge.pilot.service.utility.serialization.Json;

public final class ProblemRouter implements RouteGroup {

    public static ProblemRouter INSTANCE = new ProblemRouter();

    private final ProblemFacade problemFacade = ProblemFacade.INSTANCE;

    private ProblemRouter() {

    }

    @Override
    public void addRoutes() {
        path("/problems", () -> {
            get("/", (request, response) -> {
                final List<Problem> problems = problemFacade.retrieveAll();
                return Json.json(problems, response);
            });
            post("/", (request, response) -> {
                final ProblemInvoice invoice = Json.body(ProblemInvoice.class, request);
                final IdAware idAware = problemFacade.create(invoice);
                return Json.json(idAware.justId(), response);
            });
            post("/random", (request, response) -> {
                final ProblemRandomInvoice invoice = Json.body(ProblemRandomInvoice.class, request);
                final List<Problem> problems = problemFacade.retrieveRandom(invoice);
                return Json.json(problems, response);
            });
        });
    }
}
