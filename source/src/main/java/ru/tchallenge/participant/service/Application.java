package ru.tchallenge.participant.service;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

import spark.RouteGroup;
import static spark.Spark.*;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import ru.tchallenge.participant.service.domain.account.AccountRouter;
import ru.tchallenge.participant.service.domain.event.EventRouter;
import ru.tchallenge.participant.service.domain.problem.ProblemRouter;
import ru.tchallenge.participant.service.domain.specialization.SpecializationRouter;
import ru.tchallenge.participant.service.domain.workbook.WorkbookRouter;
import ru.tchallenge.participant.service.security.SecurityRouter;
import ru.tchallenge.participant.service.security.authentication.AuthenticationInterceptor;

@Slf4j
public final class Application implements Runnable {

    public static void main(String... arguments) {
        new Application().run();
    }

    private final AccountRouter accountRouter = AccountRouter.INSTANCE;
    private final AuthenticationInterceptor authenticationInterceptor = AuthenticationInterceptor.INSTANCE;
    private final EventRouter eventRouter = EventRouter.INSTANCE;
    private final ProblemRouter problemRouter = ProblemRouter.INSTANCE;
    private final SecurityRouter securityRouter = SecurityRouter.INSTANCE;
    private final SpecializationRouter specializationRouter = SpecializationRouter.INSTANCE;
    private final WorkbookRouter workbookRouter = WorkbookRouter.INSTANCE;

    @Override
    public void run() {
        log.info("Application started...");
        registerErrorHandlers();
        registerInterceptors();
        registerDomainRouters();
        registerSecurityRouters();
        registerUtilityRouters();
    }

    private void registerErrorHandlers() {
        exception(Exception.class, (exception, request, response) -> {
            log.error("Requested operation terminated with an error", exception);
            response.status(400);
            response.body(exception.getMessage());
        });
    }

    private void registerInterceptors() {
        before("/*", authenticationInterceptor::before);
        after("/*", authenticationInterceptor::after);
        before("/*", (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Request-Method", "*");
            response.header("Access-Control-Allow-Headers", "*");
        });
    }

    private void registerDomainRouters() {
        registerRoutersFromRoot(
                accountRouter,
                eventRouter,
                problemRouter,
                specializationRouter,
                workbookRouter
        );
    }

    private void registerSecurityRouters() {
        registerRoutersFromRoot(securityRouter);
    }

    private void registerRoutersFromRoot(final RouteGroup... routers) {
        Arrays.asList(routers).forEach((router) -> path("/", router));
    }

    private void registerUtilityRouters() {
        final String specification;
        try {
            final URL specificationUrl = Resources.getResource("specification/openapi.yaml");
            specification = Resources.toString(specificationUrl, Charsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        get("/specification", (request, response) -> {
            response.header("Content-Type", "application/yaml");
            return specification;
        });
        get("/version", (request, response) -> {
            return "1.0.0-SNAPSHOT";
        });
    }
}
