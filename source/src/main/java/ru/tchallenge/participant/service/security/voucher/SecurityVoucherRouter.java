package ru.tchallenge.participant.service.security.voucher;

import spark.RouteGroup;
import static spark.Spark.path;
import static spark.Spark.post;

import static ru.tchallenge.participant.service.utility.serialization.Json.body;
import static ru.tchallenge.participant.service.utility.serialization.Json.json;

public final class SecurityVoucherRouter implements RouteGroup {

    public static final SecurityVoucherRouter INSTANCE = new SecurityVoucherRouter();

    private final SecurityVoucherFacade securityVoucherFacade = SecurityVoucherFacade.INSTANCE;

    private SecurityVoucherRouter() {

    }

    @Override
    public void addRoutes() {
        path("/vouchers", () -> {
            post("/", (request, response) -> {
                final SecurityVoucherInvoice invoice = body(SecurityVoucherInvoice.class, request);
                final SecurityVoucher voucher = securityVoucherFacade.createAndSend(invoice);
                return json(voucher.getId(), response);
            });
        });
    }
}
