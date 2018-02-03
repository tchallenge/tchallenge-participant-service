package ru.tchallenge.participant.service.domain.workbook;

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
        path("/", () -> {
            post("", (request, response) -> {
                final WorkbookInvoice invoice = body(WorkbookInvoice.class, request);
                final IdAware idAware = workbookManager.create(invoice);
                return json(idAware.justId(), response);
            });
            path(":id", () -> {
                get("", (request, response) -> {
                    final String id = request.params("id");
                    final Workbook workbook = workbookManager.get(id);
                    return json(workbook, response);
                });
                put("/status", (request, response) -> {
                    final String id = request.params("id");
                    final WorkbookUpdateInvoice invoice = body(WorkbookUpdateInvoice.class, request);
                    workbookManager.updateStatus(id, invoice);
                    return json(response);
                });
            });
        });
    }
}
