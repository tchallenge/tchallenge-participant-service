package ru.tchallenge.pilot.service.security.voucher;

import spark.RouteGroup;
import static spark.Spark.path;
import static spark.Spark.post;

import ru.tchallenge.pilot.service.context.GenericApplicationComponent;
import ru.tchallenge.pilot.service.context.ManagedComponent;
import ru.tchallenge.pilot.service.utility.serialization.Json;

@ManagedComponent
public class SecurityVoucherRouter extends GenericApplicationComponent implements RouteGroup {

    private SecurityVoucherFacade securityVoucherFacade;

    @Override
    public void addRoutes() {
        path("/vouchers", () -> {
            post("/", (request, response) -> {
                final SecurityVoucherInvoice invoice = Json.body(SecurityVoucherInvoice.class, request);
                final SecurityVoucher voucher = securityVoucherFacade.createAndSend(request, invoice);
                return Json.json(voucher.getId(), response);
            });
        });
    }

    @Override
    public void init() {
        super.init();
        this.securityVoucherFacade = getComponent(SecurityVoucherFacade.class);
    }
}
