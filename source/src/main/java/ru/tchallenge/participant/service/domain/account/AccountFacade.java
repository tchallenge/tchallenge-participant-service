package ru.tchallenge.participant.service.domain.account;

import spark.Request;
import spark.Response;

public final class AccountFacade {

    public static Object create(Request request, Response response) {
        throw new UnsupportedOperationException();
    }

    public static Object retrieve(Request request, Response response) {
        return AccountManager.retrieveById(request.params("id"));
    }

    public static Object updatePassword(Request request, Response response) {
        throw new UnsupportedOperationException();
    }

    public static Object updatePersonality(Request request, Response response) {
        throw new UnsupportedOperationException();
    }

    public static Object updateStatus(Request request, Response response) {
        throw new UnsupportedOperationException();
    }

    private AccountFacade() {

    }
}
