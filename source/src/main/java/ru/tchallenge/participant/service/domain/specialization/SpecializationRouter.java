package ru.tchallenge.participant.service.domain.specialization;

import spark.RouteGroup;

import static ru.tchallenge.participant.service.utility.serialization.Json.*;
import static spark.Spark.*;

public final class SpecializationRouter implements RouteGroup {

    public static SpecializationRouter INSTANCE = new SpecializationRouter();

    private final SpecializationManager specializationManager = SpecializationManager.INSTANCE;

    private SpecializationRouter() {

    }

    @Override
    public void addRoutes() {
        path("/specializations", () -> {
            get("/", (request, response) -> {
                return json(specializationManager.retrieveByAll(), response);
            });
        });
    }
}
