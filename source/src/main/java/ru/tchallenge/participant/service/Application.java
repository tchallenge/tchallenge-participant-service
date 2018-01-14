package ru.tchallenge.participant.service;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.tchallenge.participant.service.domain.account.AccountFacade;
import ru.tchallenge.participant.service.security.authentication.AuthenticationFacade;
import ru.tchallenge.participant.service.security.token.TokenFacade;
import ru.tchallenge.participant.service.security.voucher.SecurityVoucherFacade;
import spark.Spark;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import static ru.tchallenge.participant.service.PersistenceManager.collection;
import static ru.tchallenge.participant.service.utility.serialization.Json.asJson;
import static ru.tchallenge.participant.service.utility.serialization.Json.body;
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

        final String specification;
        try {
            final URL specificationUrl = Resources.getResource("specification/openapi.yaml");
            specification = Resources.toString(specificationUrl, Charsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        before("/*", AuthenticationFacade::authenticate);

        get("/mongotest", (request, response) -> {
            return collection("test").find().map(document -> document);
        }, asJson());

        post("/mongotest", (request, response) -> {
            Map<?, ?> invoice = body(request, Map.class);
            Document newDocument = new Document();
            newDocument.put("name", invoice.get("name"));
            collection("test").insertOne(newDocument);
            return collection("test").find().map(document -> document);
        }, asJson());

        get("/specification", (request, response) -> {
            response.header("Content-Type","application/yaml");
            return specification;
        });

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

        path("/accounts", () -> {
            post("/", AccountFacade::create, asJson());
            path("/:id", () -> {
                get("", AccountFacade::retrieve, asJson());
                put("/password", AccountFacade::updatePassword, asJson());
                put("/personality", AccountFacade::updatePersonality, asJson());
                put("/status", AccountFacade::updateStatus, asJson());
            });
        });
    }
}
