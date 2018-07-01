package ru.tchallenge.pilot.service.domain.account;

import spark.RouteGroup;
import static spark.Spark.*;

import ru.tchallenge.pilot.service.utility.serialization.Json;

public final class AccountRouter implements RouteGroup {

    public static final AccountRouter INSTANCE = new AccountRouter();

    private final AccountManager accountManager = AccountManager.INSTANCE;

    private AccountRouter() {

    }

    @Override
    public void addRoutes() {
        path("/accounts", () -> {
            path("/current", () -> {
                get("", (request, response) -> {
                    final Account account = accountManager.retrieveCurrent(request);
                    return Json.json(account, response);
                });
                put("/password", (request, response) -> {
                    final AccountPasswordUpdateInvoice invoice = Json.body(AccountPasswordUpdateInvoice.class, request);
                    accountManager.updateCurrentPassword(request, invoice);
                    return Json.json(response);
                });
                put("/personality", (request, response) -> {
                    final AccountPersonality invoice = Json.body(AccountPersonality.class, request);
                    accountManager.updateCurrentPersonality(request, invoice);
                    return Json.json(response);
                });
                put("/status", (request, response) -> {
                    final AccountStatusUpdateInvoice invoice = Json.body(AccountStatusUpdateInvoice.class, request);
                    accountManager.updateCurrentStatus(request, invoice);
                    return Json.json(response);
                });
            });
        });
    }
}
