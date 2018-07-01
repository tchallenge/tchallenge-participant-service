package ru.tchallenge.pilot.service.domain.specialization;

import spark.RouteGroup;

import static spark.Spark.get;
import static spark.Spark.path;

import ru.tchallenge.pilot.service.utility.serialization.Json;

public final class SpecializationRouter implements RouteGroup {

    public static SpecializationRouter INSTANCE = new SpecializationRouter();

    private final SpecializationManager specializationManager = SpecializationManager.INSTANCE;

    private SpecializationRouter() {

    }

    @Override
    public void addRoutes() {
        path("/specializations", () -> {
            get("/", (request, response) -> Json.json(specializationManager.retrieveByAll(), response));
        });
    }
}
