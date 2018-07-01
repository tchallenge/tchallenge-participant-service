package ru.tchallenge.pilot.service.security.voucher;

import spark.RouteGroup;
import static spark.Spark.path;
import static spark.Spark.post;

import ru.tchallenge.pilot.service.utility.serialization.Json;

public final class SecurityVoucherRouter implements RouteGroup {

    public static final SecurityVoucherRouter INSTANCE = new SecurityVoucherRouter();

    private final SecurityVoucherFacade securityVoucherFacade = SecurityVoucherFacade.INSTANCE;

    private SecurityVoucherRouter() {

    }

    @Override
    public void addRoutes() {
        path("/vouchers", () -> {
            post("/", (request, response) -> {
                final SecurityVoucherInvoice invoice = Json.body(SecurityVoucherInvoice.class, request);
                final SecurityVoucher voucher = securityVoucherFacade.createAndSend(invoice);
                return Json.json(voucher.getId(), response);
            });
        });
    }
}
