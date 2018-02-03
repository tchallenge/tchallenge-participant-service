package ru.tchallenge.participant.service.security.registration;

import spark.RouteGroup;

import static ru.tchallenge.participant.service.utility.serialization.Json.body;
import static ru.tchallenge.participant.service.utility.serialization.Json.json;
import static spark.Spark.post;

public final class SecurityRegistrationRouter implements RouteGroup {

    @Override
    public void addRoutes() {
        post("/", (request, response) -> {
            final SecurityRegistrationInvoice invoice = body(SecurityRegistrationInvoice.class, request);
            final SecurityRegistration registration = SecurityRegistrationManager.create(invoice);
            return json(registration.getId(), response);
        });
    }
}
