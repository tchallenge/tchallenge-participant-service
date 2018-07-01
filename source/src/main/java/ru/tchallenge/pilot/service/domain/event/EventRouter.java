package ru.tchallenge.pilot.service.domain.event;

import spark.Request;
import spark.RouteGroup;

import com.google.common.collect.Sets;
import static spark.Spark.get;
import static spark.Spark.path;

import ru.tchallenge.pilot.service.utility.data.Id;
import ru.tchallenge.pilot.service.utility.serialization.Json;

public final class EventRouter implements RouteGroup {

    public static final EventRouter INSTANCE = new EventRouter();

    private final EventManager eventManager = EventManager.INSTANCE;

    private EventRouter() {

    }

    @Override
    public void addRoutes() {
        path("events/", () -> {
            get("/", (request, response) -> {
                final EventSearchInvoice invoice = searchInvoice(request);
                final EventSearchResult result = eventManager.retrieveSearchResult(request, invoice);
                return Json.json(result, response);
            });
            get("/:id", (request, response) -> {
                final Id id = new Id(request.params("id"));
                final Event result = eventManager.retrieveById(request, id);
                return Json.json(result, response);
            });
        });
    }

    private EventSearchInvoice searchInvoice(final Request request) {
        final EventFilter filter = EventFilter.builder()
                .admittedOnly(false)
                .permalink(request.queryParams("permalink"))
                .statuses(Sets.newHashSet(EventStatus.values()))
                .build();
        final String limit = request.queryParamOrDefault("limit", "10");
        final String offset = request.queryParamOrDefault("offset", "0");
        return EventSearchInvoice.builder()
                .filter(filter)
                .limit(Integer.parseInt(limit))
                .offset(Integer.parseInt(offset))
                .build();
    }
}
