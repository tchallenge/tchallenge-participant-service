package ru.tchallenge.pilot.service.domain.specialization;

import spark.RouteGroup;

import static spark.Spark.get;
import static spark.Spark.path;

import ru.tchallenge.pilot.service.context.GenericApplicationComponent;
import ru.tchallenge.pilot.service.context.ManagedComponent;
import ru.tchallenge.pilot.service.utility.serialization.Json;

@ManagedComponent
public class SpecializationRouter extends GenericApplicationComponent implements RouteGroup {

    private SpecializationManager specializationManager;

    @Override
    public void init() {
        super.init();
        this.specializationManager = getComponent(SpecializationManager.class);
    }

    @Override
    public void addRoutes() {
        path("/specializations", () -> {
            get("/", (request, response) -> Json.json(specializationManager.retrieveByAll(request), response));
        });
    }
}
