package ru.tchallenge.participant.service.domain.event;

import com.google.common.collect.Sets;
import spark.RouteGroup;

import static ru.tchallenge.participant.service.utility.serialization.Json.json;
import static spark.Spark.*;

public final class EventRouter implements RouteGroup {

    public static final EventRouter INSTANCE = new EventRouter();

    @Override
    public void addRoutes() {
        path("/", () -> {
            get("", (request, response) -> {
                final EventFilter filter = EventFilter.builder()
                        .admittedOnly(false)
                        .textcode(request.queryParams("textcode"))
                        .statuses(Sets.newHashSet())
                        .build();
                final String limit = request.queryParamOrDefault("limit", "10");
                final String offset = request.queryParamOrDefault("offset", "0");
                final EventSearchInvoice invoice = EventSearchInvoice.builder()
                        .filter(filter)
                        .limit(Integer.parseInt(limit))
                        .offset(Integer.parseInt(offset))
                        .build();
                final EventSearchResult result = eventManager.retrieveSearchResult(invoice);
                return json(result, response);
            });
            get("/:id", (request, response) -> {
                final String id = request.params("id");
                final Event result = eventManager.retrieveById(id);
                return json(result, response);
            });
        });
    }

    private final EventManager eventManager = EventManager.INSTANCE;

    private EventRouter() {

    }
}
