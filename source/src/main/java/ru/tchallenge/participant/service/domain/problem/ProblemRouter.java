package ru.tchallenge.participant.service.domain.problem;

import spark.RouteGroup;

import static ru.tchallenge.participant.service.utility.serialization.Json.json;
import static spark.Spark.get;
import static spark.Spark.path;

public final class ProblemRouter implements RouteGroup {

    public static ProblemRouter INSTANCE = new ProblemRouter();

    private final ProblemManager problemManager = ProblemManager.INSTANCE;

    private ProblemRouter() {

    }

    @Override
    public void addRoutes() {
        path("/problems", () -> {
            get("/", (request, response) -> {
                return json(problemManager.retrieveAll(), response);
            });
        });
    }
}
