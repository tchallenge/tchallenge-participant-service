package ru.tchallenge.participant.service.domain.specialization;

import spark.RouteGroup;

import static ru.tchallenge.participant.service.utility.serialization.Json.json;
import static spark.Spark.get;
import static spark.Spark.path;

public final class SpecializationRouter implements RouteGroup {

    public static SpecializationRouter INSTANCE = new SpecializationRouter();

    private final SpecializationManager specializationManager = SpecializationManager.INSTANCE;

    private SpecializationRouter() {

    }

    @Override
    public void addRoutes() {
        path("/specializations", () -> {
            get("/", (request, response) -> json(specializationManager.retrieveByAll(), response));
        });
    }
}
