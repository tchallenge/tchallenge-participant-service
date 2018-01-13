package ru.tchallenge.participant.service.security.voucher;

import spark.Request;
import spark.Response;

import static ru.tchallenge.participant.service.utility.serialization.Json.body;

public final class SecurityVoucherFacade {

    public static String create(Request request, Response response) {
        SecurityVoucherInvoice invoice = body(request, SecurityVoucherInvoice.class);
        if (invoice == null || !invoice.isValid()) {
            throw new RuntimeException("Security voucher invoice is missing or invalid");
        }
        return SecurityVoucherManager.create(invoice);
    }

    private SecurityVoucherFacade() {

    }
}
