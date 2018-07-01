package ru.tchallenge.pilot.service.security.registration;

import spark.RouteGroup;
import static spark.Spark.path;
import static spark.Spark.post;

import ru.tchallenge.pilot.service.context.GenericApplicationComponent;
import ru.tchallenge.pilot.service.context.ManagedComponent;
import ru.tchallenge.pilot.service.utility.serialization.Json;

@ManagedComponent
public class SecurityRegistrationRouter extends GenericApplicationComponent implements RouteGroup {

    private SecurityRegistrationFacade securityRegistrationFacade;

    @Override
    public void init() {
        super.init();
        this.securityRegistrationFacade = getComponent(SecurityRegistrationFacade.class);
    }

    @Override
    public void addRoutes() {
        path("/registrations", () -> {
            post("/", (request, response) -> {
                final SecurityRegistrationInvoice invoice = Json.body(SecurityRegistrationInvoice.class, request);
                final SecurityRegistration registration = securityRegistrationFacade.createAndSendVoucher(request, invoice);
                return Json.json(registration.getId(), response);
            });
        });
    }
}
