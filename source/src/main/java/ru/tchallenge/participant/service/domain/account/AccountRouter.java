package ru.tchallenge.participant.service.domain.account;

import spark.RouteGroup;

import static ru.tchallenge.participant.service.utility.serialization.Json.json;
import static spark.Spark.*;

public final class AccountRouter implements RouteGroup {

    @Override
    public void addRoutes() {
        post("/", (request, response) -> {
            throw new UnsupportedOperationException();
        });
        path("/:id", () -> {
            get("", (request, response) -> {
                final String id = request.params("id");
                final Account account = AccountManager.retrieveById(id);
                return json(account.justId(), response);
            });
            put("/password", (request, response) -> {
                throw new UnsupportedOperationException();
            });
            put("/personality", (request, response) -> {
                throw new UnsupportedOperationException();
            });
            put("/status", (request, response) -> {
                throw new UnsupportedOperationException();
            });
        });
    }
}
