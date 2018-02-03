package ru.tchallenge.participant.service;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.tchallenge.participant.service.domain.account.AccountRouter;
import ru.tchallenge.participant.service.domain.assignment.AssignmentRouter;
import ru.tchallenge.participant.service.domain.event.EventRouter;
import ru.tchallenge.participant.service.security.SecurityRouter;
import ru.tchallenge.participant.service.security.authentication.AuthenticationFacade;

import java.io.IOException;
import java.net.URL;

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

        get("/specification", (request, response) -> {
            response.header("Content-Type","application/yaml");
            return specification;
        });

        get("/version", (request, response) -> {
            return "1.0.0-SNAPSHOT";
        });

        path("/security", new SecurityRouter());

        path("/accounts", AccountRouter.INSTANCE);

        path("/events", EventRouter.INSTANCE);

        path("/assignments", AssignmentRouter.INSTANCE);

        exception(Exception.class, (e, request, response) -> {
            response.status(400);
            response.body(e.getMessage());
        });
    }
}
