package ru.tchallenge.participant.service.security.token;

import spark.RouteGroup;
import static spark.Spark.*;

import static ru.tchallenge.participant.service.utility.serialization.Json.json;

public final class SecurityTokenRouter implements RouteGroup {

    public static final SecurityTokenRouter INSTANCE = new SecurityTokenRouter();

    private final TokenFacade tokenFacade = TokenFacade.INSTANCE;

    private SecurityTokenRouter() {

    }

    @Override
    public void addRoutes() {
        path("/tokens", () -> {
            post("/", (request, response) -> {
                final SecurityToken token = tokenFacade.createForCurrentAccount();
                return json(token.getId(), response);
            });
            path("/current", () -> {
                get("", (request, response) -> {
                    final SecurityToken token = tokenFacade.retrieveCurrent();
                    return json(token, response);
                });
                delete("", (request, response) -> {
                    tokenFacade.deleteCurrent();
                    return json(response);
                });
            });
        });
    }
}
