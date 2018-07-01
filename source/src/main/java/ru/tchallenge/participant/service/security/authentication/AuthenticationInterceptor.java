package ru.tchallenge.participant.service.security.authentication;

import spark.Request;
import spark.Response;

public interface AuthenticationInterceptor {

    void after(Request request, Response response);

    void before(Request request, Response response);
}
