package ru.tchallenge.participant.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.tchallenge.participant.service.security.token.TokenFacade;
import ru.tchallenge.participant.service.security.voucher.SecurityVoucherFacade;

import static ru.tchallenge.participant.service.utility.serialization.Json.asJson;
import static spark.Spark.*;

public class Application implements Runnable {

    public static void main(String... arguments) {
        new Application().run();
    }

    private final String applicationName = "T-Challenge Participant Service";
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run() {

        log.info("{} started...", applicationName);

        get("/version", (request, response) -> {
            return "1.0.0-SNAPSHOT";
        });

        path("/security", () -> {

            path("/tokens", () -> {
                post("/", TokenFacade::create, asJson());
                path("/current", () -> {
                    get("", TokenFacade::retrieve, asJson());
                    delete("", TokenFacade::delete, asJson());
                });
            });

            path("/vouchers", () -> {
                post("/", SecurityVoucherFacade::create, asJson());
            });

        });
    }
}
