package ru.tchallenge.pilot.service.security.registration;

import spark.RouteGroup;
import static spark.Spark.path;
import static spark.Spark.post;

import ru.tchallenge.pilot.service.utility.serialization.Json;

public final class SecurityRegistrationRouter implements RouteGroup {

    public static final SecurityRegistrationRouter INSTANCE = new SecurityRegistrationRouter();

    private final SecurityRegistrationFacade securityRegistrationFacade = SecurityRegistrationFacade.INSTANCE;

    private SecurityRegistrationRouter() {

    }

    @Override
    public void addRoutes() {
        path("/registrations", () -> {
            post("/", (request, response) -> {
                final SecurityRegistrationInvoice invoice = Json.body(SecurityRegistrationInvoice.class, request);
                final SecurityRegistration registration = securityRegistrationFacade.createAndSendVoucher(invoice);
                return Json.json(registration.getId(), response);
            });
        });
    }
}
