package ru.tchallenge.participant.service.domain.account;

import spark.RouteGroup;

import static ru.tchallenge.participant.service.utility.serialization.Json.body;
import static ru.tchallenge.participant.service.utility.serialization.Json.json;
import static spark.Spark.*;

public final class AccountRouter implements RouteGroup {

    public static final AccountRouter INSTANCE = new AccountRouter();

    @Override
    public void addRoutes() {
        path("/current", () -> {
            get("", (request, response) -> {
                final Account account = accountManager.retrieveCurrent();
                return json(account, response);
            });
            put("/password", (request, response) -> {
                final AccountPasswordUpdateInvoice invoice = body(AccountPasswordUpdateInvoice.class, request);
                accountManager.updateCurrentPassword(invoice);
                return json(response);
            });
            put("/personality", (request, response) -> {
                final AccountPersonality invoice = body(AccountPersonality.class, request);
                accountManager.updateCurrentPersonality(invoice);
                return json(response);
            });
            put("/status", (request, response) -> {
                final AccountStatusUpdateInvoice invoice = body(AccountStatusUpdateInvoice.class, request);
                accountManager.updateCurrentStatus(invoice);
                return json(response);
            });
        });
    }

    private final AccountManager accountManager = AccountManager.INSTANCE;

    private AccountRouter() {

    }
}
