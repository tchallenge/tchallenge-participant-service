package ru.tchallenge.pilot.service;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

import spark.RouteGroup;
import static spark.Spark.*;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import ru.tchallenge.pilot.service.context.ApplicationContext;
import ru.tchallenge.pilot.service.domain.account.AccountRouter;
import ru.tchallenge.pilot.service.domain.event.EventRouter;
import ru.tchallenge.pilot.service.domain.problem.ProblemRepository;
import ru.tchallenge.pilot.service.domain.problem.ProblemRouter;
import ru.tchallenge.pilot.service.domain.specialization.SpecializationRouter;
import ru.tchallenge.pilot.service.domain.workbook.WorkbookRouter;
import ru.tchallenge.pilot.service.security.SecurityRouter;
import ru.tchallenge.pilot.service.security.authentication.AuthenticationInterceptor;
import ru.tchallenge.pilot.service.security.authentication.AuthenticationInterceptorBean;
import ru.tchallenge.pilot.service.security.authentication.AuthenticationManager;
import ru.tchallenge.pilot.service.utility.serialization.Json;

@Slf4j
public final class Application implements Runnable {

    public static void main(String... arguments) {
        new Application().run();
    }

    private ApplicationContext context;
    private AccountRouter accountRouter;
    private AuthenticationInterceptor authenticationInterceptor;
    private EventRouter eventRouter;
    private ProblemRouter problemRouter;
    private SecurityRouter securityRouter;
    private SpecializationRouter specializationRouter;
    private WorkbookRouter workbookRouter;

    @Override
    public void run() {
        log.info("Application started...");
        this.context = new ApplicationContext();
        this.context.init();
        this.accountRouter = this.context.getComponent(AccountRouter.class);
        this.authenticationInterceptor = this.context.getComponent(AuthenticationInterceptorBean.class);
        this.eventRouter = this.context.getComponent(EventRouter.class);
        this.problemRouter = this.context.getComponent(ProblemRouter.class);
        this.securityRouter = this.context.getComponent(SecurityRouter.class);
        this.specializationRouter = this.context.getComponent(SpecializationRouter.class);
        this.workbookRouter = this.context.getComponent(WorkbookRouter.class);
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
        options("/*", (request, response) -> Json.json(response));
        before("/*", authenticationInterceptor::before);
        after("/*", authenticationInterceptor::after);
        before("/*", (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE");
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
