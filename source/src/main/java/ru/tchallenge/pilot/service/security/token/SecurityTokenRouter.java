package ru.tchallenge.pilot.service.security.token;

import spark.RouteGroup;
import static spark.Spark.*;

import ru.tchallenge.pilot.service.utility.serialization.Json;

public final class SecurityTokenRouter implements RouteGroup {

    public static final SecurityTokenRouter INSTANCE = new SecurityTokenRouter();

    private final TokenFacade tokenFacade = TokenFacade.INSTANCE;

    private SecurityTokenRouter() {

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
