package ru.tchallenge.pilot.service.domain.account;

import spark.RouteGroup;
import static spark.Spark.*;

import ru.tchallenge.pilot.service.context.GenericApplicationComponent;
import ru.tchallenge.pilot.service.context.ManagedComponent;
import ru.tchallenge.pilot.service.utility.serialization.Json;

@ManagedComponent
public class AccountRouter extends GenericApplicationComponent implements RouteGroup {

    private AccountManager accountManager;

    @Override
    public void init() {
        super.init();
        this.accountManager = getComponent(AccountManager.class);
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
