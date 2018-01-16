package ru.tchallenge.participant.service.security.token;

import ru.tchallenge.participant.service.security.authentication.Authentication;
import ru.tchallenge.participant.service.security.authentication.AuthenticationContext;
import spark.RouteGroup;

import static ru.tchallenge.participant.service.utility.serialization.Json.json;
import static spark.Spark.*;

public final class SecurityTokenRouter implements RouteGroup {

    @Override
    public void addRoutes() {
        post("/", (request, response) -> {
            final Authentication authentication = AuthenticationContext.getAuthentication();
            final SecurityToken token = TokenManager.create(authentication.getAccountId());
            return json(token.justId(), response);
        });
        path("/current", () -> {
            get("", (request, response) -> {
                final Authentication authentication = AuthenticationContext.getAuthentication();
                final SecurityToken token = TokenManager.retrieveByPayload(authentication.getTokenPayload());
                return json(token, response);
            });
            delete("", (request, response) -> {
                final Authentication authentication = AuthenticationContext.getAuthentication();
                TokenManager.deleteByPayload(authentication.getTokenPayload());
                return json(response);
            });
        });
    }
}
