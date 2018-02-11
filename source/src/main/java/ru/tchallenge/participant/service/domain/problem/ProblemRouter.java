package ru.tchallenge.participant.service.domain.problem;

import java.util.List;

import spark.RouteGroup;
import static spark.Spark.*;

import ru.tchallenge.participant.service.utility.data.IdAware;
import static ru.tchallenge.participant.service.utility.serialization.Json.body;
import static ru.tchallenge.participant.service.utility.serialization.Json.json;

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
                return json(problems, response);
            });
            post("/", (request, response) -> {
                final ProblemInvoice invoice = body(ProblemInvoice.class, request);
                final IdAware idAware = problemFacade.create(invoice);
                return json(idAware.justId(), response);
            });
            post("/random", (request, response) -> {
                final ProblemRandomInvoice invoice = body(ProblemRandomInvoice.class, request);
                final List<Problem> problems = problemFacade.retrieveRandom(invoice);
                return json(problems, response);
            });
        });
    }
}
