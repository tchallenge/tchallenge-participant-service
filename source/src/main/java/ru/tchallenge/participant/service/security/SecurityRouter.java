package ru.tchallenge.participant.service.security;

import ru.tchallenge.participant.service.security.registration.SecurityRegistrationRouter;
import ru.tchallenge.participant.service.security.token.SecurityTokenRouter;
import ru.tchallenge.participant.service.security.voucher.SecurityVoucherRouter;
import spark.RouteGroup;

import static spark.Spark.*;

public final class SecurityRouter implements RouteGroup {

    @Override
    public void addRoutes() {
        path("/registrations", new SecurityRegistrationRouter());
        path("/tokens", new SecurityTokenRouter());
        path("/vouchers", new SecurityVoucherRouter());
    }
}
