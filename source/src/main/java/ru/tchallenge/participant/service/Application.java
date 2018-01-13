package ru.tchallenge.participant.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    }
}
