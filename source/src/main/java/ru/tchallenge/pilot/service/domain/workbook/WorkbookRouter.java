package ru.tchallenge.pilot.service.domain.workbook;

import ru.tchallenge.pilot.service.domain.workbook.assignment.AssignmentUpdateInvoice;
import ru.tchallenge.pilot.service.utility.data.Id;
import ru.tchallenge.pilot.service.utility.data.IdAware;
import ru.tchallenge.pilot.service.utility.serialization.Json;

import spark.RouteGroup;

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
                final WorkbookInvoice invoice = Json.body(WorkbookInvoice.class, request);
                final IdAware idAware = workbookManager.create(request, invoice);
                return Json.json(idAware.justId(), response);
            });
            path("/:id", () -> {
                get("", (request, response) -> {
                    final Id id = new Id(request.params("id"));
                    final Workbook workbook = workbookManager.retrieveById(request, id);
                    return Json.json(workbook, response);
                });
                put("/assignments/:index", (request, response) -> {
                    final Id id = new Id(request.params("id"));
                    final Integer index = Integer.parseInt(request.params("index"));
                    final AssignmentUpdateInvoice invoice = Json.body(AssignmentUpdateInvoice.class, request);
                    workbookManager.updateAssignment(request, id, index, invoice);
                    return Json.json(response);
                });
                put("/status", (request, response) -> {
                    final Id id = new Id(request.params("id"));
                    final WorkbookStatusUpdateInvoice invoice = Json.body(WorkbookStatusUpdateInvoice.class, request);
                    workbookManager.updateStatus(request, id, invoice);
                    return Json.json(response);
                });
            });
        });
    }
}
