package ru.tchallenge.participant.service.security;

import spark.RouteGroup;

import static spark.Spark.path;

import ru.tchallenge.participant.service.security.registration.SecurityRegistrationRouter;
import ru.tchallenge.participant.service.security.token.SecurityTokenRouter;
import ru.tchallenge.participant.service.security.voucher.SecurityVoucherRouter;

public final class SecurityRouter implements RouteGroup {

    public static final SecurityRouter INSTANCE = new SecurityRouter();

    private SecurityRouter() {

    }

    @Override
    public void addRoutes() {
        path("/registrations", new SecurityRegistrationRouter());
        path("/tokens", new SecurityTokenRouter());
        path("/", SecurityVoucherRouter.INSTANCE);
    }
}
