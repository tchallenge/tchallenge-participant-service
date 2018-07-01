package ru.tchallenge.pilot.service.security;

import spark.RouteGroup;
import static spark.Spark.path;

import ru.tchallenge.pilot.service.security.registration.SecurityRegistrationRouter;
import ru.tchallenge.pilot.service.security.token.SecurityTokenRouter;
import ru.tchallenge.pilot.service.security.voucher.SecurityVoucherRouter;

public final class SecurityRouter implements RouteGroup {

    public static final SecurityRouter INSTANCE = new SecurityRouter();

    private SecurityRouter() {

    }

    @Override
    public void addRoutes() {
        path("/security", () -> {
            path("/", SecurityRegistrationRouter.INSTANCE);
            path("/", SecurityTokenRouter.INSTANCE);
            path("/", SecurityVoucherRouter.INSTANCE);
        });
    }
}
