package ru.tchallenge.participant.service.security.registration;

import spark.RouteGroup;

import static ru.tchallenge.participant.service.utility.serialization.Json.asJson;
import static ru.tchallenge.participant.service.utility.serialization.Json.body;
import static spark.Spark.post;

public final class SecurityRegistrationRouter implements RouteGroup {

    @Override
    public void addRoutes() {
        post("/", (request, response) -> {
            final SecurityRegistrationInvoice invoice = body(request, SecurityRegistrationInvoice.class);
            final Object result = SecurityRegistrationManager.create(invoice);
            return asJson().render(result);
        });
    }
}
