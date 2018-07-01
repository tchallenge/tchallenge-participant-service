package ru.tchallenge.pilot.service.security.token;

import spark.RouteGroup;
import static spark.Spark.*;

import ru.tchallenge.pilot.service.context.GenericApplicationComponent;
import ru.tchallenge.pilot.service.context.ManagedComponent;
import ru.tchallenge.pilot.service.utility.serialization.Json;

@ManagedComponent
public class SecurityTokenRouter extends GenericApplicationComponent implements RouteGroup {

    private TokenFacade tokenFacade;

    @Override
    public void init() {
        super.init();
        this.tokenFacade = getComponent(TokenFacade.class);
    }

    @Override
    public void addRoutes() {
        path("/tokens", () -> {
            post("/", (request, response) -> {
                final SecurityToken token = tokenFacade.createForCurrentAccount(request);
                return Json.json(token, response);
            });
            path("/current", () -> {
                get("", (request, response) -> {
                    final SecurityToken token = tokenFacade.retrieveCurrent(request);
                    return Json.json(token, response);
                });
                put("/delete", (request, response) -> {
                    tokenFacade.deleteCurrent(request);
                    return Json.json(response);
                });
            });
        });
    }
}
