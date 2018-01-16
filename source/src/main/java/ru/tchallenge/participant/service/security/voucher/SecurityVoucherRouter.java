package ru.tchallenge.participant.service.security.voucher;

import spark.RouteGroup;

import static ru.tchallenge.participant.service.utility.serialization.Json.body;
import static ru.tchallenge.participant.service.utility.serialization.Json.json;
import static spark.Spark.*;

public final class SecurityVoucherRouter implements RouteGroup {

    @Override
    public void addRoutes() {

        post("/", (request, response) -> {
            final SecurityVoucherInvoice invoice = body(SecurityVoucherInvoice.class, request);
            final SecurityVoucher voucher = SecurityVoucherManager.create(invoice);
            return json(voucher.justId(), response);
        });
    }
}
