package ru.tchallenge.participant.service.security.registration;

import spark.RouteGroup;
import static spark.Spark.path;
import static spark.Spark.post;

import static ru.tchallenge.participant.service.utility.serialization.Json.body;
import static ru.tchallenge.participant.service.utility.serialization.Json.json;

public final class SecurityRegistrationRouter implements RouteGroup {

    public static final SecurityRegistrationRouter INSTANCE = new SecurityRegistrationRouter();

    private final SecurityRegistrationFacade securityRegistrationFacade = SecurityRegistrationFacade.INSTANCE;

    private SecurityRegistrationRouter() {

    }

    @Override
    public void addRoutes() {
        path("/registrations", () -> {
            post("/", (request, response) -> {
                final SecurityRegistrationInvoice invoice = body(SecurityRegistrationInvoice.class, request);
                final SecurityRegistration registration = securityRegistrationFacade.createAndSendVoucher(invoice);
                return json(registration.getId(), response);
            });
        });
    }
}
