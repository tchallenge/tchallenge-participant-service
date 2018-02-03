package ru.tchallenge.participant.service.domain.assignment;

import spark.RouteGroup;

import static ru.tchallenge.participant.service.utility.serialization.Json.body;
import static ru.tchallenge.participant.service.utility.serialization.Json.json;
import static spark.Spark.put;
import static spark.Spark.path;

public final class AssignmentRouter implements RouteGroup {

    public static final AssignmentRouter INSTANCE = new AssignmentRouter();

    private AssignmentRouter() {

    }

    private final AssignmentManager assignmentManager = AssignmentManager.INSTANCE;

    @Override
    public void addRoutes() {
        path("/", () -> {
            put(":id", (request, response) -> {
                final String id = request.params("id");
                final AssignmentUpdateInvoice invoice = body(AssignmentUpdateInvoice.class, request);
                assignmentManager.updateSolution(id, invoice);
                return json(response);
            });
        });
    }
}
