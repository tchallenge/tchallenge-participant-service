package ru.tchallenge.pilot.service.security;

import spark.RouteGroup;
import static spark.Spark.path;

import ru.tchallenge.pilot.service.context.GenericApplicationComponent;
import ru.tchallenge.pilot.service.context.ManagedComponent;
import ru.tchallenge.pilot.service.security.registration.SecurityRegistrationRouter;
import ru.tchallenge.pilot.service.security.token.SecurityTokenRouter;
import ru.tchallenge.pilot.service.security.voucher.SecurityVoucherRouter;

@ManagedComponent
public class SecurityRouter extends GenericApplicationComponent implements RouteGroup {

    @Override
    public void addRoutes() {
        path("/security", () -> {
            path("/", getComponent(SecurityRegistrationRouter.class));
            path("/", getComponent(SecurityTokenRouter.class));
            path("/", getComponent(SecurityVoucherRouter.class));
        });
    }
}
