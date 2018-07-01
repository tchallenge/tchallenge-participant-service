package ru.tchallenge.pilot.service.domain.event;

import spark.Request;
import spark.RouteGroup;

import com.google.common.collect.Sets;
import static spark.Spark.get;
import static spark.Spark.path;

import ru.tchallenge.pilot.service.context.GenericApplicationComponent;
import ru.tchallenge.pilot.service.context.ManagedComponent;
import ru.tchallenge.pilot.service.utility.data.Id;
import ru.tchallenge.pilot.service.utility.serialization.Json;

@ManagedComponent
public class EventRouter extends GenericApplicationComponent implements RouteGroup {

    private EventManager eventManager;

    @Override
    public void init() {
        super.init();
        this.eventManager = getComponent(EventManager.class);
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
