package ru.tchallenge.participant.service.domain.workbook;

import ru.tchallenge.participant.service.domain.workbook.assignment.AssignmentUpdateInvoice;
import ru.tchallenge.participant.service.utility.data.Id;
import ru.tchallenge.participant.service.utility.data.IdAware;
import spark.RouteGroup;

import static ru.tchallenge.participant.service.utility.serialization.Json.*;
import static spark.Spark.*;

public final class WorkbookRouter implements RouteGroup {

    public static WorkbookRouter INSTANCE = new WorkbookRouter();

    private final WorkbookManager workbookManager = WorkbookManager.INSTANCE;

    private WorkbookRouter() {

    }

    @Override
    public void addRoutes() {
        path("/workbooks", () -> {
            post("/", (request, response) -> {
                final WorkbookInvoice invoice = body(WorkbookInvoice.class, request);
                final IdAware idAware = workbookManager.create(invoice);
                return json(idAware.justId(), response);
            });
            path("/:id", () -> {
                get("", (request, response) -> {
                    final Id id = new Id(request.params("id"));
                    final Workbook workbook = workbookManager.retrieveById(id);
                    return json(workbook, response);
                });
                put("/assignments/:index", (request, response) -> {
                    final Id id = new Id(request.params("id"));
                    final Integer index = Integer.parseInt(request.params("index"));
                    final AssignmentUpdateInvoice invoice = body(AssignmentUpdateInvoice.class, request);
                    workbookManager.updateAssignment(id, index, invoice);
                    return json(response);
                });
                put("/status", (request, response) -> {
                    final Id id = new Id(request.params("id"));
                    final WorkbookStatusUpdateInvoice invoice = body(WorkbookStatusUpdateInvoice.class, request);
                    workbookManager.updateStatus(id, invoice);
                    return json(response);
                });
            });
        });
    }
}
